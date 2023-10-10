-- Insert sample data into Receptionist table
INSERT INTO
    Receptionist(receptionistID, receptionistName, Salary)
VALUES
    ('REC001', 'John Doe', 2500);
    -- Insert sample data into Patient table
INSERT INTO
    Patient(
        patientID,
        patientName,
        age,
        gender
    )
VALUES
    ('PAT001', 'Alice Jones', '25', 'F'),
    ('PAT002', 'Bob Smith', '40', 'M'),
    ('PAT003', 'Charlie Brown', '35', 'M');

UPDATE
    Patient
SET
    username = 'alicejones',
    password = 'mypassword'
WHERE
    patientID = 'PAT001';

UPDATE
    Patient
SET
    username = 'bobsmith',
    password = 'mypassword'
WHERE
    patientID = 'PAT002';

UPDATE
    Patient
SET
    username = 'charliebrown',
    password = 'mypassword'
WHERE
    patientID = 'PAT003';

-- Insert sample data into Doctor table
INSERT INTO
    Doctor(doctorID, doctorName, department)
VALUES
    ('DOC001', 'Dr. John Smith', 'Cardiology'),
    ('DOC002', 'Dr. Sarah Lee', 'Pediatrics'),
    ('DOC003', 'Dr. Tom Johnson', 'Orthopedics');

-- Insert sample data into Appointment table
INSERT INTO
    Appointment(
        appointmentID,
        Appointmentdate,
        doctorID,
        patientID
    )
VALUES
    (
        'APPT001',
        '2023-05-01 10:00:00',
        'DOC001',
        'PAT001'
    ),
    (
        'APPT002',
        '2023-05-03 14:30:00',
        'DOC002',
        'PAT002'
    ),
    (
        'APPT003',
        '2023-05-05 11:15:00',
        'DOC003',
        'PAT003'
    );

-- Update username and password for Dr. John Smith
UPDATE
    Doctor
SET
    username = 'johnsmith',
    password = 'password123'
WHERE
    doctorID = 'DOC001';

-- Update username and password for Dr. Sarah Lee
UPDATE
    Doctor
SET
    username = 'sarahlee',
    password = 'password456'
WHERE
    doctorID = 'DOC002';

-- Update username and password for Dr. Tom Johnson
UPDATE
    Doctor
SET
    username = 'tomjohnson',
    password = 'password789'
WHERE
    doctorID = 'DOC003';

-- Insert sample data into ElectronicPrescription table
INSERT INTO
    ElectronicPrescription(
        prescriptionID,
        patientID,
        doctorID,
        medicationName,
        dosage,
        frequency
    )
VALUES
    (
        'RX001',
        'PAT001',
        'DOC001',
        'Lisinopril',
        '10mg',
        'Once a day'
    ),
    (
        'RX002',
        'PAT002',
        'DOC002',
        'Amoxicillin',
        '500mg',
        'Twice a day'
    ),
    (
        'RX003',
        'PAT003',
        'DOC003',
        'Ibuprofen',
        '200mg',
        'As needed'
    );

-- Insert sample data into HealthRecords table
INSERT INTO
    HealthRecords(summary, isCured, currentStatus, ID)
VALUES
    (
        'Patient had blood pressure.',
        'No',
        'Ongoing',
        'PAT001'
    ),
    (
        'Patient had a mild infection.',
        'Yes',
        'Cured',
        'PAT002'
    ),
    (
        'Patient had joint pain.',
        'No',
        'Ongoing',
        'PAT003'
    );

-- Insert sample data into HospitalStaff table
INSERT INTO
    HospitalStaff(ID, staffName, department, salary, performance)
VALUES
    (
        'DOC001',
        'Dr. John Smith',
        'Cardiology',
        8000,
        9
    ),
    ('DOC002', 'Dr. Sarah Lee', 'Pediatrics', 7000, 8),
    (
        'DOC003',
        'Dr. Tom Johnson',
        'Orthopedics',
        8000,
        5
    ),
    ('REC001', 'John Doe', 'Reception', 2500, 7);
    -- Insert sample data into Billing table
INSERT INTO
    Billing(
        billID,
        patientID,
        totalAmount,
        amountPaid,
        dueDate,
        currStatus
    )
VALUES
    (
        'BILL001',
        'PAT001',
        500,
        0,
        '2023-06-01',
        'Unpaid'
    ),
    (
        'BILL002',
        'PAT002',
        750,
        750,
        '2023-06-05',
        'Paid'
    ),
    (
        'BILL003',
        'PAT003',
        1000,
        500,
        '2023-06-10',
        'Partially Paid'
    );