package com.yanxing;

import org.junit.Test;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * greenDao自动生成代码
 * Created by lishuangxiang on 2016/2/18.
 */
public class DaoGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        // first parameter for version, <span></span> second for default generate package
        Schema schema = new Schema(1, "com.yanxing.model");

//        addNote(schema);
//        addCustomerOrder(schema);
        addStudent(schema);
//        addHospital(schema);
        // set dao class generate package
        schema.setDefaultJavaPackageDao("com.yanxing.dao");
        // keep custom code block
        schema.enableKeepSectionsByDefault();
        new DaoGenerator().generateAll(schema, "src/main/java-gen");
    }

    private static void addHospital(Schema schema){
        Entity hospital = schema.addEntity("HospitalEntity");
        hospital.addIdProperty().primaryKey().autoincrement();
        hospital.addStringProperty("h_id").unique();
        hospital.addStringProperty("secret_key");
        hospital.addStringProperty("code");
        hospital.addStringProperty("name");
        hospital.addStringProperty("v_role_id");
        hospital.addStringProperty("v_department_code");
        hospital.addStringProperty("profile");
        hospital.addStringProperty("features");
        hospital.addStringProperty("phone");
        hospital.addStringProperty("address");
        hospital.addStringProperty("province");
        hospital.addStringProperty("city");
        hospital.addStringProperty("county");
        hospital.addStringProperty("v_department_rank_id");
        hospital.addStringProperty("v_department_mold_id");
        hospital.addStringProperty("is_rec");
        hospital.addStringProperty("sort");
        hospital.addStringProperty("img");
        hospital.addStringProperty("ratio");
        hospital.addStringProperty("date_name");
        hospital.addStringProperty("date_code");
        hospital.addStringProperty("insert_time");
    }


    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addStudent(Schema schema) {
        Entity student = schema.addEntity("Student");
        student.setTableName("user");
        student.addIdProperty().primaryKey().autoincrement();
        student.addStringProperty("name");
        student.addStringProperty("sex");
        student.addStringProperty("birth");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
