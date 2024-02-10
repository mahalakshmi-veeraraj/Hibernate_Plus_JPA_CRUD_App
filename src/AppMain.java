import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppMain {
	public static void main(String[] args) {
		HibernateUtil hibernateUtil = new HibernateUtil();
		System.out.println("Hibernate Configuration Completed");
		System.out.println("Session Factory Creation Completed");
		EmployeeDAO employeeDAO = new EmployeeDAO();
		Optional<EmployeeDTO> employeeDTOOptional = employeeDAO.findById(10);
		EmployeeDTO foundEmployee = employeeDTOOptional.get();
		System.out.println(foundEmployee.getName() + "   " + foundEmployee.getDesignation());

		System.out.println("Find All Employee Results are as follows:");
		Optional<List<EmployeeDTO>> listEmployeeDTOOptional = employeeDAO.findAll();
		List<EmployeeDTO> listEmployeeDTO = listEmployeeDTOOptional.orElse(new ArrayList<>());
		for (EmployeeDTO employeeDTO : listEmployeeDTO) {
			System.out.println(employeeDTO.getName() + "   " + employeeDTO.getDesignation());
		}

		EmployeeDTO saveEmployeeDTO = new EmployeeDTO();
		saveEmployeeDTO.setName("Sachet");
		saveEmployeeDTO.setGender("Female");
		saveEmployeeDTO.setPhone("1234567890");
		saveEmployeeDTO.setEmail("st@gmail.com");
		saveEmployeeDTO.setDesignation("QA");
		saveEmployeeDTO.setSalary(282030);
		employeeDAO.save(saveEmployeeDTO);

		saveEmployeeDTO.setEmail("testsachet@gmail.com");
		employeeDAO.update(saveEmployeeDTO);

		employeeDAO.delete(11);
	}
}
