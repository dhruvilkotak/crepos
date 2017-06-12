# crepos
Web Based Repository : web based file management system. This web application allow to create directories with some privilege. According user role or access permissions this web application allow to access the files.

The system has 3 users: Admin, Manager and Employee
Employee:

• Should be able to register with personal details (First Name, Last Name, Address, Phone
number, personal Email, username, password).
• Facility to raise a request for a Vacation/Sick leave to his/her immediate manager.
• Update the personal profile.
• Upload / Download documents.

Admin:

• Should be able to see the list of Employees who are newly registered and inactive.
• Should be able to see the list of Employees and Managers who are already active in the
system.
• Should be able to assign any new Employee under the supervision of any Manager in the
system.
• Able to update the Organization profile (Division Name, Role, Under the supervision) of
any Employee.
• Able to run payroll and generate paycheck for each employee annually.
• By default, each employee should get 4 leaves.

Manager:

• Each Manager is an employee.
• A manager can have any number of immediate Managers or immediate Employees.
• Should be able to assign bonus to his/her immediate sub managers or immediate sub
employees and it applies to the current month.
• Accept/Decline employees leave request.
• Should be able to allow another-team-employee (ATE - employee who is not in the
current manager Tree but working in other Team) to access a directory with respect to
the protected access permission provided by the current manager.

Directory:

• Any number of directories can be created by a Manager.
o Access right permission can be given to each directory and those permissions only applies to his/her sub employees.
• The files created/accessed by the current Manager can also be viewed by the super managers (all the managers who are above the current manager) irrespective of the access permission.
• If the current manager has a file shared by the super manager (Manager above the current manager), the current manager can restrict the shared directory permission (applicable to only protected and default).
(i.e., If the manager shared a directory with protected or default permission level, the sub employees can restrict to private).

Directory Access Permissions:

Public
Should be visible to all the employees in the system.

Private
Only be visible to the current manager and all the super managers.

Default
Visible to Current Manager Tree (till leaf) and all the super managers, until it is restricted.

Protected
Visible to Current Manager Tree (till leaf), Another Team Employee (ATE) and all the super managers, until it is restricted.

Public access permission on directories can only be changed (changed to private, protected or default) by the managers who created those.
