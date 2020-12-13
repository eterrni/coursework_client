package by.bsuir.kp.bean;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    private Integer id;
    private String model;
    private String type;
    private Integer count;
    private Integer year;
    private Integer flesh;
    private String status;
    private String producerName;
    private String providerName;

    public Product() {
    }

    public Product(Integer id, String model, String type, Integer count, Integer year, Integer flesh, String status, String producerName, String providerName) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.count = count;
        this.year = year;
        this.flesh = flesh;
        this.status = status;
        this.producerName = producerName;
        this.providerName = providerName;
    }

    public Product(String model, String type, Integer count, Integer year, Integer flesh, String status, String producerName, String providerName) {
        this.model = model;
        this.type = type;
        this.count = count;
        this.year = year;
        this.flesh = flesh;
        this.status = status;
        this.producerName = producerName;
        this.providerName = providerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getFlesh() {
        return flesh;
    }

    public void setFlesh(Integer flesh) {
        this.flesh = flesh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(model, product.model) &&
                Objects.equals(type, product.type) &&
                Objects.equals(count, product.count) &&
                Objects.equals(year, product.year) &&
                Objects.equals(flesh, product.flesh) &&
                Objects.equals(status, product.status) &&
                Objects.equals(producerName, product.producerName) &&
                Objects.equals(providerName, product.providerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, type, count, year, flesh, status, producerName, providerName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", count=" + count +
                ", year=" + year +
                ", flesh=" + flesh +
                ", status='" + status + '\'' +
                ", producerName='" + producerName + '\'' +
                ", providerName='" + providerName + '\'' +
                '}';
    }
}
