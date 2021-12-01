package models;

import java.util.Objects;

public class ClientsModel {
    private Integer client_id;
    private String first_name;
    private String last_name;
    private Boolean active;

    public ClientsModel(){
    }

    public ClientsModel(Integer client_id, String first_name, String last_name, Boolean active){
        this.client_id = client_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.active = active;
    }


    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsModel that = (ClientsModel) o;
        return Objects.equals(client_id, that.client_id) && Objects.equals(first_name, that.first_name) && Objects.equals(last_name, that.last_name) && Objects.equals(active, that.active);
    }


    @Override
    public String toString() {
        return "ClientsModel{" +
                "client_id=" + client_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", active=" + active +
                '}';
    }
}
