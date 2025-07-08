package org.example.streams.employeemodel;

import org.example.functional_interfaces.FunctionalInterfacesTest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeTest {
    private static final List<Employee> employees = Arrays.asList(
            new Employee(1, "abc", 28, 123, "F", "HR", "Blore", 2020),
            new Employee(2, "xyza", 29, 120, "F", "HR", "Hyderabad", 2015),
            new Employee(3, "efg", 30, 115, "M", "HR", "Chennai", 2014),
            new Employee(4, "def", 32, 125, "F", "HR", "Chennai", 2013),
            new Employee(5, "ijk", 22, 150, "F", "IT", "Noida", 2013),
            new Employee(6, "abc", 26, 140, "M", "IT", "Chennai", 2017),
            new Employee(7, "uvw", 26, 160, "F", "IT", "Pune", 2016),
            new Employee(8, "pqr", 23, 160, "M", "IT", "Chennai", 2015),
            new Employee(9, "stv", 25, 160, "M", "IT", "Blore", 2010)
    );

    public static void main(String[] args) {
        Map<String, List<Employee>> groupedByDesignation = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName));


        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());

        //Given List<Employee>, Get a Map which has Department, and Employee Name drawing highest salary in the department.
        Map<String, String> highestSalaryEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                                emp -> emp.map(Employee::getName).orElse(null)
                        )
                ));

        //Given List<Employee>, Return Map with Employee Name and Salary
        Map<String, Long> employeeSalaryMap = employees.stream()
                .collect(Collectors.toMap(Employee::getName, Employee::getSalary));

        //Remove duplicate names
        Set<String> seenNames = new HashSet<>();
        List<Employee> uniqueEmployees = employees.stream()
                .filter(emp -> seenNames.add(emp.getName())) // Add name to set and filter duplicates
                .collect(Collectors.toList()); // Collect to list

        Optional<Employee> secondHighest =  employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .distinct() // To avoid duplicates in case of equal salaries
                .skip(1) // Skip the highest salary
                .findFirst(); // Get the second highest salary


        //Length of the city name more than 5 characters
        employees.stream().map(Employee::getCity).filter(FunctionalInterfacesTest.predicate).forEach(FunctionalInterfacesTest.consumer);
        System.out.println();

        //1. Group the Employees by city
        Map<String, List<Employee>> employeeMapByCity = employees.stream().collect(Collectors.groupingBy(Employee::getCity));

        //2. Group the Employees by age
        Map<Integer, List<Employee>> employeeMapByAge = employees.stream().collect(Collectors.groupingBy(Employee::getAge));

        //3. Find the count of male and female employees present in the organization.
        Map<String, Long> employeeCountByGender = employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));

        //4. Find the names of all departments in the organization.
        List<String> departments = employees.stream().map(Employee::getDeptName).distinct().toList();

        // 5.  Find employee details whose age is greater than 28
        List<Employee> employeesWithAgeMore28 = employees.stream().filter(employee -> employee.getAge() > 28).toList();

        // 6. Find maximum age of employee.
        System.out.println(employees.stream().mapToInt(Employee::getAge).max().orElseThrow(() -> new NoSuchElementException("Error")));

        //7. Find Average age of Male and Female Employees
        Map<String, Double> employeeAverageAgeByGender = employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));

        //8. Find the number of employees in each department.
        Map<String, Long> employeeCountInDep = employees.stream().collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()));

        //9. Find oldest employee.
        Employee employeeOldest = employees.stream().max(Comparator.comparing(Employee::getAge)).orElseThrow(() -> new NoSuchElementException("error"));

        //10. Find youngest female employee.
        Employee employeeFemaleYonger = employees.stream().filter(employee -> employee.getGender().equals("F")).min(Comparator.comparing(Employee::getAge)).orElseThrow(() -> new NoSuchElementException("error"));

        //11. Find employees whose age is greater than 30 and less than 30.
        //12. Find the department name which has the highest number of employees.
        Map<String, Long> departmentCount = employees.stream().collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()));
        String departmentWithMaxEmployees = departmentCount.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElseThrow(() -> new RuntimeException());

        //13. Find if there are any employees from HR Department.
        boolean hasEmployeesInHR = employees.stream().anyMatch(employee -> "HR".equals(employee.getDeptName()));

        //14. Find the department names that these employees work for, where the number of employees in the department is over 3.
        Map<String, Long> employeesByDepartments = employees.stream().collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()));
        List<String> departmentsWithMoreThan3Employees = employeesByDepartments.entrySet().stream().filter(entry -> entry.getValue() > 3).map(Map.Entry::getKey).toList();

        
        /*1. Filter Employees by Salary - Retrieve a list of employees earning more than 100,000.*/
        List<Employee> filteredBySalary = employees.stream()
                .filter(e -> e.getSalary() > 120)
                .toList();

        /*2. Group Employees by Department
        Group employees based on deptName and count the number of employees in each department.*/
        Map<String, Long> employeesByDepts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()));

        /*3. Find the Highest-Paid Employee
        Identify the employee with the highest salary using streams.*/
        Employee highestPaidEmployee = employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);

        /*4. Find the Average Age of Employees per Department
        Compute the average age of employees for each deptName.
        Sort Employees by Salary in Descending Order*/
        Map<String, Double> averageAgePerDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.averagingInt(Employee::getAge)));
        //averageAgePerDepartment.forEach((dept, average) -> System.out.println(dept + " " + average));

        /*5. Sort Employees by Salary in Descending Order
        Retrieve a list of employees sorted by salary in descending order.*/
        List<Employee> descSortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
                .toList();

        /*6. Find Employees Who Joined After a Certain Year
        Get a list of employees who joined after the year 2015.*/
        List<Employee> joinedAfterEmployees = employees.stream()
                .filter(e -> e.getYearOfJoining() > 2015)
                .toList();

        /*7. Partition Employees by Gender
        Partition employees into male and female groups using Streams.*/
        Map<Boolean, List<Employee>> partitionByGender = employees.stream()
                .collect(Collectors.partitioningBy(e -> "M".equals(e.getGender())));

        /*8. Find the Department with the Highest Average Salary
        Determine which department has the highest average salary.*/
        Optional<Map.Entry<String, Double>> depWithHighestAverageSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.averagingLong(Employee::getSalary)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
        if (depWithHighestAverageSalary.isPresent()) {
            String department = depWithHighestAverageSalary.get().getKey();
        }

        /*9. Find the Second-Highest Salary
        Retrieve the employee with the second-highest salary using Streams.*/
        Employee employeeWithSecondSalary = employees.stream()
                .sorted(Comparator.comparingLong(Employee::getSalary).reversed())
                .limit(2)
                .skip(1)
                .findAny()
                .orElse(null);

        //System.out.println(employeeWithSecondSalary.toString());

        /*10. Get Distinct Cities Where Employees Work
        Retrieve a list of unique cities where employees are working.*/
        List<String> citiesWhereWorks = employees.stream()
                .map(Employee::getCity)
                .distinct()
                .toList();

        /*11. Find the Youngest and Oldest Employee in the Company
        Retrieve the youngest and oldest employee based on age.*/
        List<Employee> empSorted = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .toList();
        Employee youngest = empSorted.getFirst();
        Employee oldest = empSorted.get(employees.size() - 1);

        /*12. Count Employees in Each City
        Count the number of employees in each city using Streams.*/
        Map<String, Long> countEmployeesByCities = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));

        /*13. Find Employees with Duplicate Names
        Identify employees having duplicate names in the company.*/
        Set<String> duplicateNames = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        /*14. Find Employees Whose Names Start with 'A'
        Retrieve all employees whose name starts with 'A'.*/
        List<Employee> nameStartsFromA = employees.stream()
                .filter(e -> e.getName().startsWith("a"))
                .toList();

        /*15. Find Employees Whose Name Length is Greater than 5
        Get a list of employees where the name has more than 5 characters.*/
        List<Employee> nameLengthMore5 = employees.stream()
                .filter(e -> e.getName().length() > 5)
                .toList();

        /*16. Calculate Total Salary Expense for the Company
        Compute the total sum of all employees' salaries.*/
        Long totalSalaryExpense = employees.stream()
                .mapToLong(Employee::getSalary)
                .sum();

        /*17. Find Employees in a Particular City and Department
        Get a list of employees who work in "New York" and belong to the "IT" department.*/
        Predicate<Employee> fromTheCityInDepPredicate = (e) -> "Chennai".equals(e.getCity()) && "IT".equals(e.getDeptName());
        List<Employee> fromTheCityInDepartment = employees.stream()
                .filter(fromTheCityInDepPredicate)
                .toList();

        /*18. Sort Employees First by Department, Then by Age
        Sort employees first by deptName and then by age in ascending order.*/
        List<Employee> sortedEmployee = employees.stream()
                .sorted(Comparator.comparing(Employee::getDeptName)
                        .thenComparing(Employee::getAge))
                .toList();

        /*19. Get the Name of the Highest-Earning Employee in Each Department
        Find the name of the top-earning employee from each department.*/
        Map<String, String> nameHighestEarningByDep = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingLong(Employee::getSalary)),
                        e -> e.map(Employee::getName).orElse("Unknown"))));

        /*20. Check if Any Employee Earns More Than 1 Million
        Use Streams to check if any employee has a salary greater than 1,000,000.*/
        boolean anyEmployeeHasSalaryBigger = employees.stream().anyMatch(e -> e.getSalary() > 150);


        /*
        Tasks for collectingAndThen ----------------------------------------------------

        1️⃣ Find the Youngest Employee in Each Department
        Task: Group employees by department and find the youngest employee’s name in each department.*/
        Map<String, String> yongestEmployeeInDep = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.collectingAndThen(
                        Collectors.minBy(Comparator.comparingInt(Employee::getAge)),
                        e -> e.map(Employee::getName).orElse("Unknown")
                )));

        /*
        2️⃣ Count the Number of Employees in Each City
        Task: Compute the count of employees in each city.*/
        Map<String, Long> countEmployeeInEachCity = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity,
                        Collectors.collectingAndThen(
                                Collectors.counting(), e -> e
                        )
                ));
        Map<String, Long> countEmployeeInCity2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));

        /*
        3️⃣ Get the Name of the Oldest Employee in the Company
        Task: Find the name of the oldest employee.*/
        String oldestEmployeeName = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingInt(Employee::getAge)),
                        e -> e.map(Employee::getName).orElse("Unknown"))
                );

        /*
        4️⃣ Get the Total Salary Expense of the Company
        Task: Compute the total sum of all employees' salaries.*/
        Long totalSalarySum = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.summingLong(Employee::getSalary),
                        total -> total
                ));

        /*
        5️⃣ Get the Department with the Highest Average Salary
        Task: Find the department where employees earn the highest average salary.
        */
        String departmentWithHighestAverageSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.averagingLong(Employee::getSalary)))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));


        /*1️⃣ Find the Most Common City Among Employees
        Task: Identify the city where the most employees are located.*/
        String cityWithMostEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()))
                .entrySet().stream()
                //.max(Map.Entry.comparingByValue())...)
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));

        /*
        2️⃣ Get the Department with the Longest Average Tenure
        Task: Find the department where employees have the highest average tenure (years since joining).*/
        String depWithLongestAverageTenure = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.averagingInt(Employee::getYearOfJoining)))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.minBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));

        /*
        3️⃣ Find the Gender That Earns the Highest Average Salary
        Task: Determine whether male or female employees have a higher average salary.*/
        String genderWithHighestAverageSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingLong(Employee::getSalary)))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));
        //System.out.println(genderWithHighestAverageSalary);

        /*1️⃣ Find the Employee Who Joined Earliest in Each Department
        Task: Group employees by department and find the earliest joining employee’s name in each department.*/
        Map<String, Employee> joinedEarliestInDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparingInt(Employee::getYearOfJoining)),
                                e -> e.orElse(null)
                        )));

        /*
        2️⃣ Identify the Most Common Age Among Employees
        Task: Find the most frequently occurring age among employees.*/
        Integer mostCommonAge = employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse(0))
                );

        /*
        3️⃣ Get the Department with the Highest Total Salary Expense
        Task: Determine which department has the highest total salary expense.*/
        String departmentWithHighestSalaryExpense = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.summingLong(Employee::getSalary)))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));

        /*
        4️⃣ Find the City with the Most Employees
        Task: Identify the city where the highest number of employees are located.*/
        String cityWithMaxEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));

        /*
        5️⃣ Get the Employee Count and Average Salary for Each Department
        Task: Group employees by department and compute both count of employees and average salary in one result.*/
        record EmployeesAndAverageSalary(Long employeesCount, Double averageSalary) {
        }
        Map<String, EmployeesAndAverageSalary> employeesAndAverageSalaryByDep = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(
                                Collectors.teeing(
                                        Collectors.counting(),
                                        Collectors.averagingLong(Employee::getSalary),
                                        EmployeesAndAverageSalary::new
                                ),
                                stats -> stats
                        )));

        /* Collectors.teeing() -------------------------------
        Intermediate Level Tasks
        1️⃣ Find the Minimum and Maximum Salary in Each Department
        Group employees by department.
        Use Collectors.teeing() to compute min salary and max salary per department.
        Store the result in a Map<String, SalaryRange> where:
        Key: Department name.
        Value: A custom object (SalaryRange) holding min & max salary.*/
        record SalaryRange(Long minSalary, Long maxSalary) {
        }
        ;
        Map<String, SalaryRange> salaryRangeByDep = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparingLong(Employee::getSalary)),
                                Collectors.maxBy(Comparator.comparingLong(Employee::getSalary)),
                                (minEmp, maxEmp) -> new SalaryRange(minEmp.get().getSalary(), maxEmp.get().getSalary())
                        ))
                );

        /*
        2️⃣ Count Employees and Find the Oldest Employee in Each Department
        Group employees by department.
        Use Collectors.teeing() to compute:
        Total count of employees.
        The oldest employee (by age).
        Store the result in Map<String, EmployeeStats> with count & oldest employee.*/
        record EmployeeStats(Long count, Employee oldest) {
        }
        ;
        Map<String, EmployeeStats> employeeStatsByDep = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.teeing(
                                Collectors.counting(),
                                Collectors.maxBy(Comparator.comparingInt(Employee::getAge)),
                                (count, emp) -> new EmployeeStats(count, emp.orElse(null))
                        )));

        /*
        3️⃣ Find the Total and Average Salary for a Given Gender
        Filter employees by gender ("Male" or "Female").
        Use Collectors.teeing() to compute:
        Total salary of that gender.
        Average salary of that gender.
        Store results in a Map<String, SalaryStats> where key = gender.*/
        record SalaryStats(long total, double average) {
        }
        ;
        Map<String, SalaryStats> salaryStatsByGender = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getGender,
                        Collectors.teeing(
                                Collectors.summingLong(Employee::getSalary),
                                Collectors.averagingLong(Employee::getSalary),
                                SalaryStats::new
                        )
                ));

        /*
        4️⃣ Calculate the Longest and Shortest Employee Name in Each Department
        Group employees by department.
        Use Collectors.teeing() to find:
        The longest employee name.
        The shortest employee name.
        Store results in a Map<String, NameStats> where key = department.*/
        record NameStats(String shortestName, String longestName) {
        }
        ;
        Map<String, NameStats> nameStatsByDep = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDeptName,
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparing(e -> e.getName().length())),
                                Collectors.maxBy(Comparator.comparing(e -> e.getName().length())),
                                (e1, e2) -> new NameStats(e1.get().getName(), e2.get().getName())
                        )
                ));


        /*
        5️⃣ Find the Youngest and Oldest Employee in the Company
        Use Collectors.teeing() to compute:
        Youngest employee (minimum age).
        Oldest employee (maximum age).
        Store results in a custom CompanyAgeStats object.*/
        record CompanyAgeStats(Employee youngest, Employee oldest) {
        }
        ;
        CompanyAgeStats companyAgeStats = employees.stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.comparingInt(Employee::getAge)),
                        Collectors.maxBy(Comparator.comparingInt(Employee::getAge)),
                        (e1, e2) -> new CompanyAgeStats(e1.orElse(null), e2.orElse(null))
                ));

        /*
        📌 Advanced Level Tasks
        6️⃣ Find the Department with the Highest and Lowest Average Salary
        Group employees by department.
        Use Collectors.teeing() to compute:
        Highest average salary department.
        Lowest average salary department.
        Store results in a DepartmentSalaryStats object.*/
        record DepartmentSalaryStats(String lowest, String highest) {
        }
        ;
        DepartmentSalaryStats departmentSalaryStats = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.averagingLong(Employee::getSalary)))
                .entrySet().stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.comparingDouble(Map.Entry::getValue)),
                        Collectors.maxBy(Map.Entry.comparingByValue()),
                        (e1, e2) -> new DepartmentSalaryStats(e1.get().getKey(), e2.get().getKey())
                ));

        /*
        7️⃣ Calculate the Number of Employees and Their Combined Experience
        Assume employees have a joining year.
        Use Collectors.teeing() to compute:
        Total number of employees.
        Sum of their experience (current year - joining year).
        Store in a CompanyExperienceStats object.*/
        record CompanyExperienceStats(Long total, Integer experience) {
        }
        ;
        CompanyExperienceStats companyExperienceStats = employees.stream()
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.summingInt((Employee e) -> 2025 - e.getYearOfJoining()),
                        CompanyExperienceStats::new
                ));
        ;

        /*
        8️⃣ Find the Gender with the Highest and Lowest Average Salary
        Group employees by gender ("Male", "Female").
        Use Collectors.teeing() to compute:
        Gender with the highest average salary.
        Gender with the lowest average salary.
        Store in a GenderSalaryComparison object.*/
        record GenderSalaryComparison(double lowest, double highest) {
        }
        GenderSalaryComparison genderSalaryComparison = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getGender,
                        Collectors.averagingLong(Employee::getSalary)
                ))
                .entrySet().stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.comparingDouble(Map.Entry::getValue)),
                        Collectors.maxBy(Comparator.comparingDouble(Map.Entry::getValue)),
                        (v1, v2) -> new GenderSalaryComparison(v1.get().getValue(), v2.get().getValue())
                ));

        /*
        9️⃣ Find the Average and Median Salary of Employees
        Use Collectors.teeing() to compute:
        Average salary of employees.
        Median salary (middle salary value after sorting).
        Store in a SalaryDistribution object.*/
        record SalaryDistribution(double average, double median) {
        }
        ;
        SalaryDistribution salaryDistribution = employees.stream()
                .map(Employee::getSalary)
                .map(Long::doubleValue)
                .collect(Collectors.teeing(
                        Collectors.averagingDouble(e -> e),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    List<Double> sorted = list.stream().sorted().toList();
                                    int size = sorted.size();
                                    if (size % 2 == 1) {
                                        return sorted.get(size / 2);
                                    } else {
                                        return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
                                    }
                                }
                        ),
                        SalaryDistribution::new
                ));

        /*
        🔟 Find the Total Salary and Highest Earner in Each City
        Group employees by city.
        Use Collectors.teeing() to compute:
        Total salary paid in each city.
        Employee with the highest salary in that city.
        Store in a CitySalaryStats object.*/
        record CitySalaryStats(Long total, Employee highestEarner) {
        }
        ;
        Map<String, CitySalaryStats> citySalaryStatsMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity,
                        Collectors.teeing(
                                Collectors.summingLong(Employee::getSalary),
                                Collectors.maxBy(Comparator.comparingLong(Employee::getSalary)),
                                (s, e) -> new CitySalaryStats(s, e.orElse(null))
                        )));


        /*Find the median of a list of integers using Java streams.*/
        List<Integer> numbers = Arrays.asList(5, 3, 1, 3, 4, 5);

        double median = numbers.stream()
                .sorted()
                .skip((numbers.size() - 1) / 2)
                .limit(2 - numbers.size() % 2)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);


        /*Using stream reduce ----------------------------------------------------------------*/


        /*1️⃣ Find the Total Salary of All Employees
        Task: Use reduce() to compute the sum of all employees' salaries.*/
        Long totalSalary = employees.stream()
                .mapToLong(Employee::getSalary)
                .reduce(0L, Long::sum);

        /*
        2️⃣ Find the Employee with the Longest Name
        Task: Use reduce() to find the employee whose name has the most characters.*/
        Employee employeeWithLongestName = employees.stream()
                .reduce((e1, e2) -> e1.getName().length() >= e2.getName().length() ? e1 : e2).orElse(null);

        /*
        3️⃣ Get the Name of the Oldest Employee
        Task: Use reduce() to find the oldest employee's name.*/
        String nameOldestEmployee = employees.stream()
                .reduce((e1, e2) -> e1.getAge() > e2.getAge() ? e1 : e2)
                .map(Employee::getName)
                .orElse(null);

        /*
        4️⃣ Compute the Product of All Employee IDs
        Task: Use reduce() to calculate the multiplication of all employee IDs.*/
        Integer idProduction = employees.stream()
                .mapToInt(Employee::getId)
                .reduce(1, (id1, id2) -> id1 * id2);

        /*
        5️⃣ Find the Employee with the Lowest Salary
        Task: Use reduce() to determine the employee with the lowest salary.*/
        Employee empWithLowestSalaryReduce = employees.stream()
                .reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e2 : e1)
                .orElse(null);

        Employee empWithLowestSalaryCollectAndThen = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.minBy(Comparator.comparingLong(Employee::getSalary)),
                        e -> e.orElse(null)
                ));

        /*
        6️⃣ Concatenate All Employee Names into a Single String
        Task: Use reduce() to create a comma-separated string of all employee names.*/
        String employeesNames = employees.stream()
                .map(Employee::getName)
                .reduce((e1, e2) -> String.join(", ", e1, e2))
                .orElse("");

        /*
        7️⃣ Count the Number of Employees Without Using count()
        Task: Use reduce() to count the total number of employees without count().*/
        Long countEmployees = employees.stream()
                .map(e -> 1L)
                .reduce(1L, Long::sum);

        /*
        8️⃣ Get the Sum of All Digits in Employee IDs
        Task: Use reduce() to compute the sum of all digits in employee IDs.*/
        Integer sumIdDigits = employees.stream()
                .map(Employee::getId)
                .reduce(0, Integer::sum);

        /*
        9️⃣ Find the Maximum Age of an Employee
        Task: Use reduce() to determine the highest employee age.*/
        Integer maxAgeEmployee = employees.stream()
                .map(Employee::getAge)
                .reduce((a, b) -> a > b ? a : b).orElse(0);

        /*
        🔟 Find the Difference Between Highest and Lowest Salaries
        Task: Use reduce() to compute the difference between the highest and lowest salary.*/
        Long highestSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce((e1, e2) -> e1 > e2 ? e1 : e2).orElse(0L);

        Long lowestSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce((s1, s2) -> s1 > s2 ? s2 : s1).orElse(0L);
        Long diff = highestSalary - lowestSalary;

        /*
        1️⃣ Find the Total Salary of All Employees (Intermediate)
        Task: Use reduce() to compute the total salary of all employees.*/
        Long totalEmployeesSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0L, Long::sum);

        /*
        2️⃣ Find the Employee with the Longest Name (Intermediate)
        Task: Use reduce() to find the employee whose name has the most characters.*/
        Employee empWithLongestName = employees.stream()
                .reduce((e1, e2) -> e1.getName().length() > e2.getName().length() ? e1 : e2)
                .orElse(null);

        /*
        3️⃣ Get the Oldest Employee's Information as a Map (Advanced)
        Task: Use reduce() to find the oldest employee and return their information as a Map<String, Object>.*/
        Optional<Employee> oldestEmployee = employees.stream()
                .reduce((e1, e2) -> e1.getAge() > e2.getAge() ? e1 : e2);

        Map<String, Object> oldestEmployeeInfo = oldestEmployee.map(e -> {
            Map<String, Object> info = new HashMap<>();
            info.put("name", e.getName());
            info.put("age", e.getAge());
            info.put("salary", e.getSalary());
            return info;
        }).orElse(null);

        /*
        4️⃣ Concatenate All Employee Names into a Single String (Advanced)
        Task: Use reduce() to create a comma-separated string of all employee names (e.g., "Alice, Bob, Charlie").*/
        String allNames = employees.stream()
                .map(Employee::getName)
                .reduce((e1, e2) -> String.join(", ", e1, e2))
                .orElse("");

        /*
        5️⃣ Find the Department with the Highest Total Salary (Advanced+)
        Task: Use reduce() to determine which department has the highest combined salary among employees.*/
        String depWithHighestTotalSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.summingLong(Employee::getSalary)))
                .entrySet().stream()
                .reduce((e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2)
                .map(Map.Entry::getKey)
                .orElse("");

        /*Tasks for skip()
        1️⃣ Get All Employees Except the First 5
        Task: Use skip() to remove the first 5 employees from the list.*/
        List<Employee> skipFirst5 = employees.stream().skip(5).toList();

        /*
        2️⃣ Find the 3rd Highest Paid Employee
        Task: Sort employees by salary in descending order, then use skip() to get the third highest-paid employee.*/
        Employee thirdHighestPaid = employees.stream()
                .sorted(Comparator.comparingLong(Employee::getSalary).reversed())
                .skip(2)
                .findFirst().orElse(null);

        /*
        Tasks for limit()
        3️⃣ Get the Top 10 Youngest Employees
        Task: Sort employees by age and use limit(10) to return the youngest 10 employees.*/
        List<Employee> top10YongestEmployee = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .limit(10)
                .toList();

        /*
        4️⃣ Get the First 5 Employees in Alphabetical Order
        Task: Sort employees by name and use limit(5) to get the first 5 employees.*/
        List<Employee> first5Sorted = employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .limit(5)
                .toList();

        /*
        Tasks for distinct() ---------------------------------------------
        5️⃣ Get a List of Unique Cities Where Employees Live
        Task: Extract the distinct cities from the employees’ list.*/
        List<String> uniqueCities = employees.stream()
                .map(Employee::getCity)
                .distinct()
                .toList();

        /*
        6️⃣ Find All Unique Departments
        Task: Use distinct() to get all unique department names.*/
        List<String> uniqueDepartments = employees.stream()
                .map(Employee::getDeptName)
                .distinct()
                .toList();

                /*
        Tasks for Collectors.toMap() ----------------------------
        7️⃣ Create a Map of Employee ID to Name
        Task: Use Collectors.toMap() to create a Map<Integer, String> where keys are employee IDs and values are names.*/
        Map<Integer, String> mapIdToName = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));

        /*
        8️⃣ Create a Map of Department to Total Salary
        Task: Use Collectors.toMap() to map each department to its total salary expense.*/
        Map<String, Long> mapDepToTotalSalary = employees.stream()
                .collect(Collectors.toMap(Employee::getDeptName, Employee::getSalary, Long::sum));

        /*1️⃣ Find the Department with the Most Employees
        Task: Create a Map<String, Long> where the key is the department name and the value is the number of employees in that department.*/
        Map<String, Long> numberEmployeesByDep = employees.stream().collect(Collectors.toMap(Employee::getDeptName, e -> 1L, Long::sum));

        /*
        2️⃣ Get the Average Salary for Each Department
        Task: Create a Map<String, Double> where the key is the department name and the value is the average salary in that department.*/
        Map<String, Double> averageSalaryByDep = employees.stream()
                .collect(Collectors.toMap(Employee::getDeptName, e -> (double) e.getSalary(), (e1, e2) -> (e1 + e2) / 2));

        Map<String, Double> averageSalaryByDep2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.averagingLong(Employee::getSalary)));

        /*
        3️⃣ Create a Map of City to Employee Count
        Task: Generate a Map<String, Long> where the key is the city name and the value is the number of employees in that city.*/
        Map<String, Long> mapCityToEmployeeCount = employees.stream()
                .collect(Collectors.toMap(Employee::getCity, e -> 1L, Long::sum));

        /*
        4️⃣ Get the Highest-Paid Employee in Each Department
        Task: Create a Map<String, String> where the key is the department name and the value is the name of the highest-paid employee in that department.*/
        Map<String, String> highestPaidByDep = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getDeptName,
                        e -> e,
                        (e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().getName()
                ));


        /*
        5️⃣ Create a Map of Department to a List of Employee Names
        Task: Generate a Map<String, List<String>> where the key is the department name and the value is a list of employee names in that department.*/
        Map<String, List<String>> namesByDep = employees.stream()
                .collect(Collectors.toMap(Employee::getDeptName,
                        e -> new ArrayList<>(List.of(e.getName())),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }));

        /* Collectors.mapping -------------------------------------

        1️⃣ Create a Map of Departments to Employee Names
        Task: Generate a Map<String, List<String>> where the key is the department name, and the value is a list of employee names in that department.
        Usage: Collectors.groupingBy() + Collectors.mapping(Employee::getName, Collectors.toList())*/
        Map<String, List<String>> mapDepsToNames = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.mapping(Employee::getName, Collectors.toList())));

        /*
        2️⃣ Get a Set of Unique Cities Where Employees Work
        Task: Collect all unique cities where employees work into a Set<String>.
        Usage: Collectors.mapping(Employee::getCity, Collectors.toSet())*/
        Set<String> uniqueCitiesWhereEmployeeWork = employees.stream()
                .collect(Collectors.mapping(Employee::getCity, Collectors.toSet()));//Yes, it can be replaced but for practice mapping we leave it as is

        /*
        3️⃣ Create a Map of Departments to the Initials of Employees
        Task: Generate a Map<String, List<String>> where the key is the department name, and the value is a list of employee initials.
        Usage: Collectors.mapping(e -> e.getName().substring(0, 1), Collectors.toList())*/
        Map<String, List<String>> mapDepsToEmployeeInitials = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDeptName,
                        Collectors.mapping(e -> e.getName().substring(0, 1), Collectors.toList())));

        /*
        4️⃣ Get a Map of Cities to a Concatenated String of Employee Names
        Task: Create a Map<String, String> where the key is the city name, and the value is a comma-separated string of employee names in that city.
        Usage: Collectors.mapping(Employee::getName, Collectors.joining(", "))*/
        Map<String, String> namesByCities = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.mapping(Employee::getName, Collectors.joining(", "))));

        /*
        5️⃣ Find the Longest Employee Name in Each Department
        Task: Generate a Map<String, String> where the key is the department name, and the value is the longest employee name in that department.
        Usage: Collectors.mapping(Employee::getName, Collectors.maxBy(Comparator.comparingInt(String::length)))*/
        Map<String, String> longestNameByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDeptName,
                        Collectors.mapping(Employee::getName,
                                Collectors.maxBy(Comparator.comparingInt(String::length)))))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().orElse("Unknown")));

        /*Here are five tasks that require using Stream.iterate() for practice:

        1️⃣ Generate a Sequence of Even Numbers
        Task: Use Stream.iterate() to generate the first 20 even numbers and collect them into a List<Integer>.*/
        List<Integer> evenSequence = Stream.iterate(2, a -> a + 2)
                .limit(20)
                .toList();

        /*
        2️⃣ Create a Sequence of Fibonacci Numbers
        Task: Use Stream.iterate() to generate the first 15 Fibonacci numbers and collect them into a List<Long>.*/
        List<Long> fibonacciSequence = Stream.iterate(new long[]{0, 1}, (a) -> new long[]{a[1], a[0] + a[1]})
                .limit(10)
                .map(l -> l[0])
                .toList();

        /*
        3️⃣ Generate a Sequence of Dates Starting from Today
        Task: Use Stream.iterate() to generate the next 10 days from the current date and collect them into a List<LocalDate>.*/
        List<LocalDate> sequenceOfDate = Stream.iterate(LocalDate.now(), (d1) -> d1.plusDays(1))
                .limit(10)
                .toList();

        /*
        4️⃣ Find the First 5 Multiples of a Given Number
        Task: Given a number N, use Stream.iterate() to generate its first 5 multiples and collect them into a List<Integer>.*/
        List<Integer> multiplesGivenNumber = Stream.iterate(6, n -> n + 6).limit(5).toList();

        /*
        5️⃣ Generate a Decreasing Sequence of Numbers
        Task: Use Stream.iterate() to create a decreasing sequence from 100 down to 50, collecting the values into a List<Integer>.*/
        List<Integer> numbersSequence = Stream.iterate(100, n -> n - 1)
                .limit(50)
                .toList();

        /*Here are five tasks that require using Stream.generate() for practice:

        1️⃣ Generate a Stream of Random Numbers
        Task: Use Stream.generate() to create a stream of 10 random integers between 1 and 100 and collect them into a List<Integer>.*/
        Random num = new Random();
        List<Integer> randomNumbers = Stream.generate(() -> num.nextInt(101)).limit(10).toList();

        /*
        2️⃣ Create a Stream of UUIDs
        Task: Use Stream.generate() to generate 5 unique UUIDs and collect them into a List<String>.*/
        List<String> randomUUIDs = Stream.generate(UUID::randomUUID)
                .limit(5)
                .map(UUID::toString)
                .toList();

        /*
        3️⃣ Generate a Stream of Repeated Strings
        Task: Use Stream.generate() to create a stream of 7 repeated "Hello, World!" strings and collect them into a List<String>.*/
        List<String> generatedSameStrings = Stream.generate(() -> "Hello, world!").limit(7).toList();

        /*
        4️⃣ Simulate Dice Rolls
        Task: Use Stream.generate() to simulate rolling a six-sided die 15 times, collecting the results into a List<Integer>.*/
        Random random = new Random();
        List<Integer> diceRolls = Stream.generate(() -> random.nextInt(6) + 1).limit(15).toList();


        /*
        5️⃣ Generate a Stream of Fixed Values
        Task: Use Stream.generate() to create a stream of 10 default employee names ("John Doe") and collect them into a List<String>.*/
        List<String> defaultNames = Stream.generate(() -> "John Doe").limit(10).toList();

        /*Advanced Java Stream Tasks 🚀------------------------------*/

        /*Find the Longest Employee Name in the Company
        Retrieve the employee whose name has the maximum number of characters.*/
        String longestNameInCompany = employees.stream()
                .max(Comparator.comparing((e1) -> e1.getName().length())).get().getName();

        String longestNameInCompany2 = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(e1 -> e1.getName().length())),
                        e -> e.map(Employee::getName).orElse("Unknown")
                ));

        /*Find the Median Salary of Employees
        Compute the median salary from the list of employees using Streams.*/
        Double medianSalary = employees.stream()
                .map(Employee::getSalary)
                .sorted()
                .skip((employees.size() - 1) / 2)
                .limit(2 - employees.size() % 2)
                .mapToLong(Long::longValue)
                .average()
                .orElse(Double.NaN);

        /*Find Employees Who Have the Same Salary
        Identify employees who share the same salary and group them accordingly.*/
        Map<Long, List<Employee>> employeesBySalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getSalary));



    /*Identify the Department with the Highest Employee Retention
    Find the department where employees have the longest average tenure (using yearOfJoining).*/
        String departmentWithLongestRetention = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.averagingInt(e -> 2025 - e.getYearOfJoining())))
                .entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(Map.Entry::getValue)),
                        e -> e.map(Map.Entry::getKey).orElse("Unknown")
                ));
        /*Given an ArrayList of integers, find all unique numbers (numbers that appear only once).*/
        //List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        List<Integer> uniqueNumbers = numbers.stream().filter(n -> Collections.frequency(numbers, n) == 1).toList();

        //Find the largest number in a given ArrayList of integers.
        Integer largestNumber = numbers.stream().max(Integer::compareTo).orElse(0);

        //Print each number in the list along with its frequency.
        Map<Integer, Long> numberWithFrequency = numbers.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting()));

        //Identify all duplicate numbers in the list.
        List<Integer> duplicateNumbers = numbers.stream().filter(n -> Collections.frequency(numbers, n) > 1).toList();

        /*Identify the longest string in a given array of strings.*/
        String[] strings = {"apple", "banana", "cherry", "watermelon"};
        String longestString = Arrays.stream(strings).max(Comparator.comparing(String::length)).orElse("Unknown");

        /*Count the frequency of each character in a string (including spaces).*/
        String input = "hello world";
        Map<Character, Long> charFrequency = input.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    /*Find the Kth Highest Paid Employee
    Retrieve the employee with the Kth highest salary dynamically using Streams.*/
        int k = 3;
        Employee kthPaidEmployee = employees.stream()
                .sorted(Comparator.comparingLong(Employee::getSalary).reversed())
                .skip(k - 1)
                .findFirst()
                .orElse(null);

    /*Find the City With the Most Employees
    Determine which city has the highest number of employees.*/
        String cityWithMostNumberEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");

    /*Find Employees Who Are Earning Less Than the Average Salary
    Compute the average salary and retrieve employees earning below that.*/
        double averageSalary = employees.stream().mapToLong(Employee::getSalary).average().orElse(0.0);
        List<Employee> employeesEarnsLessThanAverage = employees.stream()
                .filter(e -> e.getSalary() < averageSalary)
                .toList();

    /*Check if All Departments Have At Least One Female Employee
    Validate if every department contains at least one female employee.
    */
        Boolean allDepsHaveFemale = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDeptName))
                .values().stream()
                .allMatch(depEmployees -> depEmployees.stream().anyMatch(e -> "female".equalsIgnoreCase(e.getGender())) );

    /*
    🔹 Intermediate Tasks (7)
    Flatten and Filter Emails
    Given a list of users, each with a list of email addresses, flatten the email list and return only valid (non-empty, containing '@') emails using flatMap.*/
        record User(String name, List<String> emails) {
        }
        List<User> users = List.of(
                new User("John", List.of("john@gmail.com", "dfsdf")),
                new User("Mike", List.of("mike@dd.com", "", "ff@ss"))
        );
        List<String> validEmails = users.stream()
                .flatMap(u -> u.emails.stream())
                .filter(email -> email != null && email.contains("@"))
                .toList();

    /*
    Extract Domain Names
    From a list of URLs, extract the domain name using mapMulti (no regex) and collect them into a Set.
    */
        List<String> urls = List.of("https://www.example.com/username/34", "https://www.google.com/search", "https://www.amazon.com");
        Set<String> domainNames = urls.stream()
                .<String>mapMulti((url, downstream) -> {
                    try {
                        String host = new URI(url).toURL().getHost();
                        downstream.accept(host);
                    } catch (URISyntaxException | MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        /*
    Filter and Limit
    Given a stream of transactions (amount, timestamp), skip the first 5 and then return the next 10 high-value ones (amount > 100) using skip, limit, and filter.*/
        record Transaction(long amount, long timestamp){}
        List<Transaction> transactions = List.of(
                new Transaction(24, 234213123325L),
                new Transaction(23, 234215123325L),
                new Transaction(22, 234213323325L),
                new Transaction(456, 234213233325L),
                new Transaction(456, 234213238325L),
                new Transaction(102, 234223233325L)
        );
        Stream<Transaction> highValueTransactions = transactions.stream()
                .skip(2)
                .filter(t -> t.amount() > 100)
                .limit(2);

        /*
    Group Words by First Character
    Group a list of words by their first character using Collectors.groupingBy.*/
        List<String> words = List.of("apple", "banana", "cherry", "watermelon", "orange", "kiwi", "grape", "lemon", "peach", "ananas");
        Map<Character, List<String>> wordsByFirstCharacter = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0), Collectors.toList()));

        /*
    Partition Numbers
    Given a list of integers, partition them into even and odd numbers using Collectors.partitioningBy.*/
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>> numsByEvenOdd = nums.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

        /*
    Unique Characters in Sorted Order
    From a list of strings, extract all unique characters and return them sorted alphabetically using flatMap, distinct, and sorted.*/
        String sorted = words.stream()
                .flatMap(w -> w.chars().mapToObj(c -> (char) c))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .reduce("", (c, con) -> con + c);
        System.out.println(sorted);

        /*Group a list of employees first by department, then by role.*/
        Map<String, Map<String, List<Employee>>> employeesByDeptAndCity = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.groupingBy(Employee::getCity)));

        /*TODO
    Generate Fibonacci Stream
    Use Stream.iterate to generate the first 20 numbers of the Fibonacci sequence.*/

        /*
    🔸 Advanced Tasks (9)
    Summarize Product Prices
    Given a list of Product objects (name, price), use Collectors.summarizingDouble to collect summary statistics for the prices.

    Find Top 3 Longest Sentences
    From a list of sentences, find top 3 longest ones using sorted with a custom Comparator and limit.

    ToMap with Duplicate Key Resolver
    Convert a list of people with email addresses to a Map<email, person> resolving duplicate keys by choosing the oldest person.

    Nested Grouping by Department and Role
    Group employees first by department, then by role using Collectors.groupingBy.

    Teeing Collector for Stats
    Use Collectors.teeing to calculate both the average and total salary of employees in one go.

    Generate Random Usernames
    Use Stream.generate to create a stream of random usernames and collect the first 100 into a list.

    Sort by Multiple Fields
    Given a list of books (title, author, year), sort them first by author, then year, then title using Comparator.thenComparing.

    Filter Using findAny and Optional Handling
    Find any product with a stock > 0 and return its name, or "None Available" using findAny and Optional.

    Map with Conditional Collector
    Create a Map<String, List<Product>> from a list of products, grouping by category but only if price > 100 using Collectors.mapping.

    🔺 Complex Tasks (4)
    Multi-Level Partitioning
    Partition a list of customers into:

    Premium (spent > 10k) vs. regular,

    Then within each, active vs. inactive,
    using partitioningBy inside another partitioningBy.

    Analyze Employee Hierarchy
    Given a stream of employees with manager IDs, use flatMap, groupingBy, and toMap to build a report of managers with their direct reports' average salary.

    Longest Word in Nested Paragraphs
    From a List<List<String>> representing paragraphs and sentences, find the longest word using flatMap, map, and max.

    Stream Pipeline Builder
    Build a stream pipeline that:

    Generates an infinite stream of timestamps

    Skips the first 100

    Filters only even seconds

    Limits to the next 50

    Maps to formatted strings

    Collects to a list*/
    }
}
/*
//----------------------------------
//TODO

Find the Department Where the Youngest Manager Works
Assume managers are employees with the highest salary in their department. Find the department where the youngest such manager works.



Generate a Summary Report of Employees by Department
Create a Map where the key is the department name, and the value is a summary of:
Total Employees
Average Salary
Youngest & Oldest Employee
Top Earner
* */
