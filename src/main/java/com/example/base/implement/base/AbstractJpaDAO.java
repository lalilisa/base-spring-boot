package com.example.base.implement.base;



import com.example.base.common.dto.QueryDto;
import com.example.base.common.dto.ResponseListAll;
import com.example.base.entity.user.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vladmihalcea.hibernate.query.SQLExtractor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract  class  AbstractJpaDAO<T extends Serializable> {
    private Class<T> clazz;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    public AbstractJpaDAO(Class<T> clazz) {
        this.clazz = clazz;
    }


    private long countListResult(Predicate predicate){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery=criteriaBuilder.createQuery(Long.class);
        Root<T> rootCount=criteriaQuery.from(clazz);
        criteriaQuery=criteriaQuery.where(predicate);
        long totalResult=entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(rootCount))).getSingleResult() ;
        return totalResult;
    }
    @SuppressWarnings("unchecked")
    public ResponseListAll findsEntity(QueryDto queryDto) throws JsonProcessingException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        CriteriaQuery<T> querySort=createQuerySort(criteriaBuilder,criteriaQuery,root,queryDto);
        Predicate finalPredicate=createQueryExecute(criteriaBuilder,querySort,root,queryDto);
        CriteriaQuery<T> queryExecute=criteriaQuery.where(finalPredicate);
        TypedQuery<T> executeQuery=entityManager.createQuery(queryExecute);
        int totalResult=(int) countListResult(finalPredicate);
        //Paging
        executeQuery.setFirstResult((queryDto.getPageNumber()-1)*queryDto.getPageSize());
        executeQuery.setMaxResults(queryDto.getPageSize());
        int totalPage=totalResult/queryDto.getPageSize() + (totalResult%queryDto.getPageSize()==0?0:1);
        int currentPage=queryDto.getPageNumber();
        List<T> data=executeQuery.getResultList();
        int currentData= data.size();
        return  new ResponseListAll(totalPage,currentPage,currentData,data);
    }
    public  Predicate createQueryExecute(CriteriaBuilder criteriaBuilder,CriteriaQuery<T> criteriaQuery,Root<T> root,QueryDto queryDto) throws JsonProcessingException {
        Predicate searchPredicate=predicateSearchString(criteriaBuilder,criteriaQuery,root,queryDto);
        Predicate filterPredicate=predicateFilter(criteriaBuilder,criteriaQuery,root,queryDto);
        return criteriaBuilder.and(searchPredicate,filterPredicate) ;

    }
    private CriteriaQuery<T> createQuerySort(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root, QueryDto queryDto) {
        if (queryDto.getSort() != null) {
            List<Order> listOrder = new ArrayList<>();
            List<String> sorts = Arrays.stream(queryDto.getSort()).map(sort -> sort).collect(Collectors.toList());
            for (String orderCol : sorts) {
                if(orderCol.contains("-")) {
                    orderCol=orderCol.replace("-","");
                    listOrder.add(criteriaBuilder.asc(root.get(orderCol)));
                }
                else
                    listOrder.add(criteriaBuilder.desc(root.get(orderCol)));
            }

            return criteriaQuery.orderBy(listOrder);
        }
        return criteriaQuery;
    }

    private Predicate predicateSearchString(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root, QueryDto queryDto) {
        List<Predicate> predicates =new ArrayList<>();
        if(queryDto.getSearch()==null)
                queryDto.setSearch("");

             predicates = Arrays.stream(queryDto.getProperty()).
                    map(field ->criteriaBuilder.or(
                            (criteriaBuilder
                                            .like(
                                                    criteriaBuilder.lower(root.<String>get(field)), "%" + queryDto.getSearch() + "%")))).collect(Collectors.toList());

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    public T create(final T entity) {
        entityManager.persist(entity);
        return entity;
    }
    public T findOne(final long id) {
        return entityManager.find(clazz, id);
    }

    public T update(final T entity) {
        return entityManager.merge(entity);
    }

    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }


    public  Predicate predicateFilter(CriteriaBuilder criteriaBuilder,CriteriaQuery<T> criteriaQuery  , Root<T> root, QueryDto queryDto) throws JsonProcessingException {
        queryDto.setFilters();
        List<Predicate> listFilters=new ArrayList<>();
        if(queryDto.getFilters()!=null) {
            queryDto.getFilters().forEach((key, value) -> {
                Predicate createPredicate=toPredicate(root,criteriaQuery,criteriaBuilder,key,value);
                listFilters.add(createPredicate);
            });

            return criteriaBuilder.and(listFilters.toArray(new Predicate[listFilters.size()]));
        }
        return criteriaBuilder.and(listFilters.toArray(new Predicate[listFilters.size()]));
    }

    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,String key,Object value) {
        int index=key.indexOf("_");
        if (index==-1)
            return builder.equal(root.get(key), value);
        String operator=key.substring(index+1,key.length()) ;
        String filed=key.substring(0,index);
        if (operator.equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(filed), value.toString());
        }
        else if (operator.equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(filed), value.toString());
        }
        else  if(operator.equalsIgnoreCase("in")){
            List<Object>listInRange= Arrays.asList(value.toString().split("\\,+"));
            return root.get(filed).in(listInRange);
        }
        return null;
    }
}
