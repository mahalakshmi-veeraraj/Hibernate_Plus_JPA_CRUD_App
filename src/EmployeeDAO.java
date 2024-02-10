import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeDAO {

	public Optional<EmployeeDTO> findById(int id) {
		Session session = null;
		EmployeeEntity employeeEntity = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			employeeEntity = session.get(EmployeeEntity.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			// session.flush();
			session.close();
		}
		return Optional.ofNullable(convertEntityToDTO(employeeEntity));
	}

	public Optional<List<EmployeeDTO>> findAll() {
		EmployeeSQLQuery employeeSQLQuery = new EmployeeSQLQuery();
		Session session = null;
		List<EmployeeDTO> listEmployeeDTO = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			List<EmployeeEntity> listEmployeeEntity = session
					.createQuery(employeeSQLQuery.findAll(), EmployeeEntity.class).getResultList();
			listEmployeeDTO = new ArrayList<>();
			for (EmployeeEntity employeeEntity : listEmployeeEntity) {
				listEmployeeDTO.add(convertEntityToDTO(employeeEntity));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// session.flush();
			session.close();
		}
		return Optional.ofNullable(listEmployeeDTO);
	}

	// try with resources
	public void save(EmployeeDTO employeeDTO) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			session.persist(converyDTOToEntity(employeeDTO));

			transaction.commit();
		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			throw e;
		}
	}

	public void update(EmployeeDTO employeeDTO) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(converyDTOToEntity(employeeDTO));
			transaction.commit();
		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			throw e;
		}
	}

	public void delete(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			EmployeeEntity employeeEntity = session.get(EmployeeEntity.class, id);
			if (null == employeeEntity)
				return;
			session.remove(employeeEntity);
			transaction.commit();
		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			throw e;
		}
	}

	private EmployeeEntity converyDTOToEntity(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setId(employeeDTO.getId());
		employeeEntity.setName(employeeDTO.getName());
		employeeEntity.setGender(employeeDTO.getGender());
		employeeEntity.setPhone(employeeDTO.getPhone());
		employeeEntity.setEmail(employeeDTO.getEmail());
		employeeEntity.setDesignation(employeeDTO.getDesignation());
		employeeEntity.setSalary(employeeDTO.getSalary());
		return employeeEntity;
	}

	private EmployeeDTO convertEntityToDTO(EmployeeEntity employeeEntity) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		if (employeeEntity == null)
			return employeeDTO;
		employeeDTO.setId(employeeEntity.getId());
		employeeDTO.setName(employeeEntity.getName());
		employeeDTO.setGender(employeeEntity.getGender());
		employeeDTO.setPhone(employeeEntity.getPhone());
		employeeDTO.setEmail(employeeEntity.getEmail());
		employeeDTO.setDesignation(employeeEntity.getDesignation());
		employeeDTO.setSalary(employeeEntity.getSalary());
		return employeeDTO;
	}
}
