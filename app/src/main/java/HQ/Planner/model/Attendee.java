package HQ.Planner.model;

import HQ.Planner.model.abstract_class_interface.Abstract_Attendee;

public class Attendee extends Abstract_Attendee {


    int id;

    String name;
    String phone;



    public Attendee(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }



    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


}
