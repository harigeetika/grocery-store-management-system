import axios from "axios";

class CustomerService{

    
    getAllCustomers(){
        return axios.get("http://localhost:8084/customer/allCustomers");
    }

    subsCust(custEmail,custName){
        return axios.post("http://localhost:8084/customer/subsCustomer",{
            custEmail:custEmail,
            custName:custName
        });
    }

    deleteCustomerById(id){
        return axios.delete("http://localhost:8084/customer/delete/"+id);
    }

   
}
export default new CustomerService();