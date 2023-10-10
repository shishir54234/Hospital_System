public class HospitalStaff {
    
        private String id;
        private String name;
        private String department;
        private int salary;
        private int performance;
    
        public HospitalStaff(String id, String name, String department, int salary, int performance) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.performance = performance;
        }
    
        public String getId() {
            return id;
        }
    
        public String getName() {
            return name;
        }
    
        public String getDepartment() {
            return department;
        }
    
        public int getSalary() {
            return salary;
        }
    
        public int getPerformance() {
            return performance;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public void setDepartment(String department) {
            this.department = department;
        }
    
        public void setSalary(int salary) {
            this.salary = salary;
        }
    
        public void setPerformance(int performance) {
            this.performance = performance;
        }
    
    }

