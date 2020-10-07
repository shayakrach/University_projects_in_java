package coronatree;

public class VaccinationRecords {
    
    AVLTree T1;  // a tree storing the subjects which await vaccination.
    AVLTree T2;	 // a tree stroring the subjects after vaccination.

    /**
     * Creates a new empty factor.
     */
    public VaccinationRecords(){
        T1 = new AVLTree();
        T2 = new AVLTree();
    }

    /**
     * Inserts a new subject into the records to await vaccination.
     *
     * You may assume that each Person has a unique ID number.
     *
     * Should run in O(log(n1)) where n1 is the size of T1.
     *
     * @param p - the new Person.
     */
    public void insert(Person p){
        T1.insert(p);
    }


    /**
     * Checks whether a given subject was vaccinated, is awaiting vaccination or is not part of the data structure.
     * A Person is considered vaccinated only if the vaccinateSubjects() function
     * was called after the Person was inserted.
     * In this case the function should return 0.
     *
     * If a Person was inserted and the vaccinateSubjects() function was not called,
     * then the Person is considered to be awaiting vaccination.
     * In this case the funcion should return 1.
     *
     * In the case the Person was never inserted the function should return 2.
     *
     * Should run in time O(log(n1) + log(n2)) where n1 and n2 are the size of T1 and T2 respectively.
     * @param p - the Person to examine.
     * @return 0,1 or 2, depending on the situation of 'p'.
     */
    public int checkVaccinated(Person p){
        if(T2.search(p)){
        	return 0;
		}
        else if(T1.search(p)){
        	return 1;
		}
        return 2;
    }


    /**
     * Returns a sorted array of all the subjects which were already vaccinated.
     * A Person is considered vaccinated only if the vaccinateSubjects() function
     * was called after the Person was inserted.
     *
     * Should run in time O(n2), where n2 is the size of T2.
     * @return an array sorted according to ID numbers.
     */
    public Person[] listVaccinatedSubjects(){
        return T2.inorder();
    }

    /**
     * Returns a sorted array of all the subject which await vaccination.
     * A Person is considered to await vaccination if it was inserted into the records and the
     * vaccinateSubjects() function was not called after insertion.
     *
     * Should run in time O(n1), where n1 is the size of T1.
     * @return an array sorted according to ID numbers.
     */
    public Person[] listNonVaccinatedSubjects(){
        return T1.inorder();
    }

    /**
     * Vaccinate all subjects which await vaccination. In essance, should move all Person objects from T1, to T2.
     * Should run in time O(nlog(n)), where n is the total number of subjects in the data structure.
     *
     */
    public void vaccinateSubjects(){
        Person [] awaitPersons = listNonVaccinatedSubjects();
        for(int i = 0; i<awaitPersons.length; i++){
        	T2.insert(awaitPersons[i]);
		}
        T1 = new AVLTree();
    }
    
    /* Once everything is completed you may run the following code to test your program.
     * Expected output:
     * Aaron, ID number: 1; Dareon, ID number: 4; 
	 *  1
	 *  2
	 *	Baron, ID number: 2; Cauron, ID number: 3; 
	 *	Aaron, ID number: 1; Dareon, ID number: 4; 
	 *	0
	 *	1
	 *  Aaron, ID number: 1; Baron, ID number: 2; Cauron, ID number: 3; Dareon, ID number: 4; 
	 *  2
     *
     *
     *	Note: passing this test does not guarantee that your code is correct. Test thoroughly, test carefully.
     */
    public static void main(String[] args){
    	VaccinationRecords rec = new VaccinationRecords();
    	Person a = new Person(1, "Aaron");
    	Person b = new Person(2, "Baron");
    	Person c = new Person(3, "Cauron");
    	Person d = new Person(4, "Dareon");

    	rec.insert(a);
    	rec.insert(d);
    	Person[] wait = rec.listNonVaccinatedSubjects();
    	for (Person p: wait) {
			System.out.print(p + "; ");
		}
    	System.out.println();
    	System.out.println(rec.checkVaccinated(a));
    	System.out.println(rec.checkVaccinated(b));
    	
    	rec.vaccinateSubjects();
    	rec.insert(b);
    	rec.insert(c);
    	wait = rec.listNonVaccinatedSubjects();
    	for (Person p: wait) {
			System.out.print(p + "; ");
		}
    	System.out.println();
    	
    	Person[] vacc = rec.listVaccinatedSubjects();
    	for (Person p: vacc) {
			System.out.print(p + "; ");
		}
    	System.out.println();
    	System.out.println(rec.checkVaccinated(a));
    	System.out.println(rec.checkVaccinated(b));
    	rec.vaccinateSubjects();
    	vacc = rec.listVaccinatedSubjects();
    	for (Person p: vacc) {
			System.out.print(p + "; ");
		}
    	System.out.println();
    	
    	System.out.println(rec.T2.height());



    }
}
