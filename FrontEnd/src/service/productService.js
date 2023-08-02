import axios from "axios";

class ProductService{

    saveProduct(productImage,productName,productCategory,description,productPrice){
        return axios.post("http://localhost:8081/product/saveProduct",
        {
            productImage:productImage,
            productName:productName,
            productCategory:productCategory,
            description:description,
            productPrice:productPrice
           },
            {
              headers:
               {
                "Content-Type":'multipart/form-data'
               }
             });
    }

    getAllProducts(){
        return axios.get("http://localhost:8081/product/allProducts");
    }

    getAllProductsByPagination(offset,pageSize){
        return axios.get("http://localhost:8081/product/sort/"+offset+"/"+pageSize);
    }

    // getSortProductsAsc(sortVal){
    //     return axios.get("http://localhost:8081/product/sort/"+sortVal+"/productPrice");
    // }


    // getSortProductsDes(sortVal){
    //     return axios.get("http://localhost:8081/product/sort/"+sortVal+"/productPrice");
    // }

    getProductById(id){
        return axios.get("http://localhost:8081/product/"+id);
    }

    deleteProductById(id){
        return axios.delete("http://localhost:8081/product/delete/"+id);
    }

    getProductsByCategory(cat){
        return axios.get("http://localhost:8081/product/cat/"+cat);
    }

    editProduct(productImage,productName,productCategory,description,productPrice,productId){
        return axios.put("http://localhost:8081/product/update/"+productId,{
            productImage:productImage,
            productName:productName,
            productCategory:productCategory,
            description:description,
            productPrice:productPrice
           },
            {
              headers:
               {
                "Content-Type":'multipart/form-data',
               }
             });
    }
}
export default new ProductService();