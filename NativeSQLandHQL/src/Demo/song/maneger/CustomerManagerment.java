package Demo.song.maneger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.spi.ServiceRegistry;

import org.apache.commons.collections.Factory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import Demo.song.entity.Customer;

// Use Native SQL
public class CustomerManagerment {
	private static SessionFactory factory;
	// truy vấn vô hướng ( Scalar)

	public void ListCustomerScalar() {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			String sql = "select id,name,email from Customer";
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List data = query.list();
			for (Object ob : data) {
				Map row = (Map) ob;
				System.out.println("ID :" + row.get("id"));
				System.out.println("Name :" + row.get("name"));
				System.out.println("Email :" + row.get("email"));
				System.out.println("_______________________________");
			}
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// truy vấn qua thực thể entity
	public void ListCustomerEntity() {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			String sql = "select * from customer";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Customer.class);
			List data = query.list();
			for (Iterator it = data.iterator(); it.hasNext();) {
				Customer c = (Customer) it.next();
				System.out.println("ID:" + c.getId());
				System.out.println("Name:" + c.getName());
				System.out.println("Email:" + c.getEmail());
				System.out.println("Phone:" + c.getPhone());
				System.out.println("Address:" + c.getAddress());
				System.out.println("_______________________________");
			}
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// methoad add Customer
	public void addCustomer(Customer c) {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			session.save(c);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// select Customer like name
	public void selectbyid(String name) {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			String hql = "from Customer E where E.name like concat('%',:name,'%')";
			Query q = session.createQuery(hql);
			q.setParameter("name", name);
			List cus = q.list();
			for (Iterator it = cus.iterator(); it.hasNext();) {
				Customer c = (Customer) it.next();
				System.out.println("ID:" + c.getId());
				System.out.println("Name:" + c.getName());
				System.out.println("Email:" + c.getEmail());
				System.out.println("Phone:" + c.getPhone());
				System.out.println("Address:" + c.getAddress());
				System.out.println("_______________________________");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure().addAnnotatedClass(Customer.class).buildSessionFactory();
			CustomerManagerment cm = new CustomerManagerment();
			// Customer c1 = new Customer(12, "Huy Hung", "hung@gmail.com",
			// "0132445", "Binh Thuan");
			// cm.addCustomer(c1);
			 cm.selectbyid("huy");
//			cm.ListCustomerEntity();
		} catch (Throwable ex) {
			System.out.println("Failed to create Session factory");
			ex.printStackTrace();
		}

	}

}
