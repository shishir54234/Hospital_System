-- Add foreign key constraint to Appointment table
ALTER TABLE
    Appointment
ADD
    CONSTRAINT fk_doctor_appointment FOREIGN KEY (doctorID) REFERENCES Doctor(doctorID);

-- Add foreign key constraint to Appointment table
ALTER TABLE
    Appointment
ADD
    CONSTRAINT fk_patient_appointment FOREIGN KEY (patientID) REFERENCES Patient(patientID);

ALTER TABLE
    Patient
ADD
    username VARCHAR(30),
ADD
    password VARCHAR(30);

ALTER TABLE
    Patient
ADD
    CONSTRAINT unique_username_patient UNIQUE (username);

-- Add username and password columns to Doctor table
ALTER TABLE
    Doctor
ADD
    username VARCHAR(30),
ADD
    password VARCHAR(30);

ALTER TABLE
    Doctor
ADD
    CONSTRAINT unique_username UNIQUE (username);

-- Add foreign key constraint to ElectronicPrescription table
ALTER TABLE
    ElectronicPrescription
ADD
    CONSTRAINT fk_patient_prescription FOREIGN KEY (patientID) REFERENCES Patient(patientID);

-- Add foreign key constraint to ElectronicPrescription table
ALTER TABLE
    ElectronicPrescription
ADD
    CONSTRAINT fk_doctor_prescription FOREIGN KEY (doctorID) REFERENCES Doctor(doctorID);

-- Add foreign key constraint to HealthRecords table
-- Add foreign key constraint to HealthRecords table
ALTER TABLE
    HealthRecords
ADD
    CONSTRAINT fk_patient_healthrecords FOREIGN KEY (ID) REFERENCES Patient(patientID);

-- Add foreign key constraint to Billing table
ALTER TABLE
    Billing
ADD
    CONSTRAINT fk_patient_billing FOREIGN KEY (patientID) REFERENCES Patient(patientID);