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

//        addCustomerOrder(schema);
        addStudent(schema);
        // set dao class generate package
        schema.setDefaultJavaPackageDao("com.yanxing.dao");
        // keep custom code block
        schema.enableKeepSectionsByDefault();
        new DaoGenerator().generateAll(schema, "src/main/java-gen");
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
