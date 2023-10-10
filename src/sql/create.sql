create table Receptionist(
    receptionistID varchar(30),
    receptionistName varchar(30),
    -- Date of registration
    Salary int,
    -- ID of the patient associated with this registration
    constraint pk_receptionist PRIMARY KEY (receptionistID) -- Set registrationID as the primary key
);

-- Create a table to store patient information
create table Patient(
    patientID varchar(30),
    -- Unique ID for each patient
    patientName varchar(30),
    -- Patient name
    age varchar(30),
    -- Patient age
    gender varchar(30),
    constraint pk_patient PRIMARY KEY (patientID) -- Set patientID as the primary key
);

-- Create a table to store doctor information
create table Doctor(
    doctorID varchar(30),
    -- Unique ID for each doctor
    doctorName varchar(30),
    -- Name of the doctor
    department varchar(30),
    -- Speciality of the doctor
    constraint pk_doctor PRIMARY KEY (doctorID) -- Set doctorID as the primary key
);

-- Create a table to store appointment information
create table Appointment(
    appointmentID varchar(30),
    -- Unique ID for each appointment
    Appointmentdate Datetime,
    -- Date and time of appointment
    doctorID varchar(30),
    patientID varchar(30),
    -- ID of the consultation associated with this appointment
    constraint pk_appointment PRIMARY KEY (appointmentID) -- Set appointmentID as the primary key
);

-- Create a table to store prescription information
create table ElectronicPrescription(
    prescriptionID varchar(30),
    patientID varchar(30),
    doctorID varchar(30),
    medicationName varchar(30),
    dosage varchar(30),
    frequency varchar(30),
    -- ID of the consultation associated with this prescription
    constraint pk_prescription PRIMARY KEY (prescriptionID) -- Set prescriptionID as the primary key
);

-- Create a table to store medication information
create table HealthRecords(
    summary varchar(30),
    -- Unique ID for each medication
    isCured varchar(30),
    -- Name of the medication
    currentStatus varchar(30),
    -- Dosage of the medication
    ID varchar(30),
    -- ID of the prescription associated with this medication
    constraint pk_healthrecords PRIMARY KEY (ID) -- Set medicationID
);

create table HospitalStaff(
    ID varchar(30),
    staffName varchar(30),
    department varchar(30),
    salary int,
    performance int,
    constraint pk_hospitalstaff PRIMARY KEY (ID) -- Set doctorID as the primary key
);

create table Billing(
    billID varchar(30),
    patientID varchar(30),
    totalAmount int,
    amountPaid int,
    dueDate Date,
    currStatus varchar(30),
    constraint pk_billing PRIMARY KEY (billID) -- Set doctorID as the primary key
);