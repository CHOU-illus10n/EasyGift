import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//文章分类列表查询
export const articleCategoryListService = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/category')
}

//文章分类添加
export const categoryAddService = (categoryData)=>{
    return request.post('/category',categoryData)
}

//文章分类修改
export const CategoryUpdateService = (categoryData)=>{
   return  request.put('/category',categoryData)
}

//文章分类删除
export const CategoryDeleteService = (id)=>{
    return request.delete('/category?id='+id)
}

export const CategoryDetailService = (id)=>{
    return request.get('/category/detail?id='+id)
}

//文章列表查询
export const articleListService = (params)=>{
   return  request.get('/article',{params:params})
}

//文章添加
export const articleAddService = (articleData)=>{
    return request.post('/article',articleData);

}

export const giftListService = (params)=>{
    return request.get('/gift',{params:params});

}

export const giftListAllService = (params)=>{
    return request.get('/gift/detail',{params:params});
}

export const giftGetService = ()=>{
    return request.get('/gift/');
}

export const giftDeleteService = (id)=>{
    return request.post('/gift/delete?id='+id);}

export const giftOrderService = (params)=>{
    return request.get('/gift/order',{params:params});
}

export const giftOrderDeleteService = (id)=>{
    return request.post('/gift/order/delete?orderId='+id);
}

export const giftCategoryService = ()=>{
    return request.get('/category');
}

export const CategoryListService = (params)=>{
    return request.get('/category/info',{params:params});
}

export const giftInfoService = (id)=>{
    return request.get('/gift/one?id='+id);
}

export const giftImgsService = (giftId)=>{
    return request.get('/gift/img?giftId='+giftId);
}


export const giftChangeStService =(params)=>{
    return request.post('/gift/change',params);
}

//查询所有商品信息
export const goodListService = (params)=>{
    return request.get('/good',{params:params});
}
//根据物品名查询信息
export const goodInfoService = (name)=>{
    return request.get('/good?goodName='+name);
}

export const goodOrderListService = (params)=>{
    return request.get('/good/order',{params:params});
}

export const goodSendService =(OrderData) =>{
    return request.post('/good/order/send',OrderData);
}

export const getGoodByIdService =(id)=>{
    return request.get('/good/getInfo?id='+id);
}

export const goodAddService =(params) =>{
    return request.post('/good',params);
}

export const deleteGoodService =(id) =>{
    return request.delete('/good?id='+id);
}

export const goodGetOneService = (id)=>{
    return request.get('/good/getOne?id='+id);
}

export const goodUpdateService = (params)=>{
    return request.post('/good/update',params);
}

export const communityListService = (params)=>{
    return request.get('/communityInfo',{params:params});
}

export const communityAddService = (params)=>{
    return request.post('/communityInfo/add',params);
}

export const communityUpdateService = (params)=>{
    return request.post('/communityInfo/update',params);
}

export const communityGetOneService = (id)=>{
    return request.get('/communityInfo/getOne?id='+id);
}

export const communityDeleteService = (id)=>{
    return request.post('/communityInfo/delete?id='+id);
}