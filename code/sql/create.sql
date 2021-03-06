DROP TABLE IF EXISTS Customer CASCADE;--OK
DROP TABLE IF EXISTS Flight CASCADE;--OK
DROP TABLE IF EXISTS Pilot CASCADE;--OK
DROP TABLE IF EXISTS Plane CASCADE;--OK
DROP TABLE IF EXISTS Technician CASCADE;--OK

DROP TABLE IF EXISTS Reservation CASCADE;--OK
DROP TABLE IF EXISTS FlightInfo CASCADE;--OK
DROP TABLE IF EXISTS Repairs CASCADE;--OK
DROP TABLE IF EXISTS Schedule CASCADE;--OK

-------------
---DOMAINS---
-------------
CREATE DOMAIN us_postal_code AS TEXT CHECK(VALUE ~ '^\d{5}$' OR VALUE ~ '^\d{5}-\d{4}$');
CREATE DOMAIN _STATUS CHAR(1) CHECK (value IN ( 'W' , 'C', 'R' ) );
CREATE DOMAIN _GENDER CHAR(1) CHECK (value IN ( 'F' , 'M' ) );
CREATE DOMAIN _CODE CHAR(2) CHECK (value IN ( 'MJ' , 'MN', 'SV' ) ); --Major, Minimum, Service
CREATE DOMAIN _PINTEGER AS int4 CHECK(VALUE > 0);
CREATE DOMAIN _PZEROINTEGER AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _YEAR_1970 AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _SEATS AS int4 CHECK(VALUE > 0 AND VALUE < 500);--Plane Seats

------------
---TABLES---
------------
CREATE TABLE Customer
(
	id INTEGER NOT NULL,
	fname CHAR(24) NOT NULL,
	lname CHAR(24) NOT NULL,
	gtype _GENDER NOT NULL,
	dob DATE NOT NULL,
	address CHAR(256),
	phone CHAR(10),
	zipcode char(10),
	PRIMARY KEY (id)
);

CREATE TABLE Pilot
(
	id INTEGER NOT NULL,
	fullname CHAR(128),
	nationality CHAR(24),
	PRIMARY KEY (id)
);

CREATE TABLE Flight
(
	fnum INTEGER NOT NULL,
	cost _PINTEGER NOT NULL,
	num_sold _PZEROINTEGER NOT NULL,
	num_stops _PZEROINTEGER NOT NULL,
	actual_departure_date DATE NOT NULL,
	actual_arrival_date DATE NOT NULL,
	arrival_airport CHAR(5) NOT NULL,-- AIRPORT CODE --
	departure_airport CHAR(5) NOT NULL,-- AIRPORT CODE --
	PRIMARY KEY (fnum)
);

CREATE TABLE Plane
(
	id INTEGER NOT NULL,
	make CHAR(32) NOT NULL,
	model CHAR(64) NOT NULL,
	age _YEAR_1970 NOT NULL,
	seats _SEATS NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE Technician
(
	id INTEGER NOT NULL,
	full_name CHAR(128) NOT NULL,
	PRIMARY KEY (id)
);

---------------
---RELATIONS---
---------------

CREATE TABLE Reservation
(
	rnum INTEGER NOT NULL,
	cid INTEGER NOT NULL,
	fid INTEGER NOT NULL,
	status _STATUS,
	PRIMARY KEY (rnum),
	FOREIGN KEY (cid) REFERENCES Customer(id),
	FOREIGN KEY (fid) REFERENCES Flight(fnum)
);

CREATE TABLE FlightInfo
(
	fiid INTEGER NOT NULL,
	flight_id INTEGER NOT NULL,
	pilot_id INTEGER NOT NULL,
	plane_id INTEGER NOT NULL,
	PRIMARY KEY (fiid),
	FOREIGN KEY (flight_id) REFERENCES Flight(fnum),
	FOREIGN KEY (pilot_id) REFERENCES Pilot(id),
	FOREIGN KEY (plane_id) REFERENCES Plane(id)
);

CREATE TABLE Repairs
(
	rid INTEGER NOT NULL,
	repair_date DATE NOT NULL,
	repair_code _CODE,
	pilot_id INTEGER NOT NULL,
	plane_id INTEGER NOT NULL,
	technician_id INTEGER NOT NULL,
	PRIMARY KEY (rid),
	FOREIGN KEY (pilot_id) REFERENCES Pilot(id),
	FOREIGN KEY (plane_id) REFERENCES Plane(id),
	FOREIGN KEY (technician_id) REFERENCES Technician(id)
);

CREATE TABLE Schedule
(
	id INTEGER NOT NULL,
	flightNum INTEGER NOT NULL,
	departure_time DATE NOT NULL,
	arrival_time DATE NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (flightNum) REFERENCES Flight(fnum)
);

----------------------------
-------- SEQUENCES ---------
----------------------------
DROP TRIGGER IF EXISTS auto_inc_plane on Plane;
DROP TRIGGER IF EXISTS auto_inc_flight on Flight;
DROP TRIGGER IF EXISTS auto_inc_tech on Technician;
DROP TRIGGER IF EXISTS auto_inc_res on Reservations;
DROP TRIGGER IF EXISTS auto_inc_pilot on Pilot;
DROP FUNCTION IF EXISTS new_plane_entry();
DROP FUNCTION IF EXISTS new_flight_entry();
DROP FUNCTION IF EXISTS new_tech_entry();
DROP FUNCTION IF EXISTS new_res_entry();
DROP FUNCTION IF EXISTS new_pilot_entry();
DROP SEQUENCE IF EXISTS plane_id;
DROP SEQUENCE IF EXISTS flight_num;
DROP SEQUENCE IF EXISTS tech_id;
DROP SEQUENCE IF EXISTS res_id;
DROP SEQUENCE IF EXISTS pilot_id;

CREATE SEQUENCE plane_id START WITH 1;
CREATE SEQUENCE flight_num START WITH 1;
CREATE SEQUENCE tech_id START WITH 1;
CREATE SEQUENCE res_id START WITH 1;
CREATE SEQUENCE pilot_id START WITH 1;

----------------------------
-- TRIGGERS AND FUNCTIONS --
----------------------------
CREATE LANGUAGE plpgsql;
CREATE FUNCTION new_pilot_entry ()
RETURNS "trigger" AS
$BODY$
BEGIN
	-- nextval - 1 because we need 0, but a sequence cannot start with 0
	NEW.id := nextval('pilot_id') - 1;
	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE LANGUAGE plpgsql;
CREATE FUNCTION new_plane_entry ()
RETURNS "trigger" AS
$BODY$
BEGIN
	-- nextval - 1 because we need 0, but a sequence cannot start with 0
	NEW.id := nextval('plane_id') - 1;
	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE LANGUAGE plpgsql;
CREATE FUNCTION new_flight_entry ()
RETURNS "trigger" AS
$BODY$
BEGIN
	-- nextval - 1 because we need 0, but a sequence cannot start with 0
	NEW.fnum := nextval('flight_num') - 1;
	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE LANGUAGE plpgsql;
CREATE FUNCTION new_tech_entry ()
RETURNS "trigger" AS
$BODY$
BEGIN
	-- nextval - 1 because we need 0, but a sequence cannot start with 0
	NEW.id := nextval('tech_id') - 1;
	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE LANGUAGE plpgsql;
CREATE FUNCTION new_res_entry()
RETURNS "trigger" AS
$BODY$
BEGIN
		NEW.rnum := nextval('res_id') - 1;
		RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER auto_inc_plane BEFORE INSERT ON Plane
FOR EACH ROW EXECUTE PROCEDURE new_plane_entry();

CREATE TRIGGER auto_inc_flight BEFORE INSERT ON Flight
FOR EACH ROW EXECUTE PROCEDURE new_flight_entry();

CREATE TRIGGER auto_inc_tech BEFORE INSERT ON Technician
FOR EACH ROW EXECUTE PROCEDURE new_tech_entry();

CREATE TRIGGER auto_inc_res BEFORE INSERT ON Reservation
FOR EACH ROW EXECUTE PROCEDURE new_res_entry();

CREATE TRIGGER auto_inc_pilot BEFORE INSERT ON Pilot
FOR EACH ROW EXECUTE PROCEDURE new_pilot_entry();

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Customer (
	id,
	fname,
	lname,
	gtype,
	dob,
	address,
	phone,
	zipcode
)
FROM 'customer.csv'
WITH DELIMITER ',';

COPY Pilot (
	id,
	fullname,
	nationality
)
FROM 'pilots.csv'
WITH DELIMITER ',';

COPY Plane (
	id,
	make,
	model,
	age,
	seats
)
FROM 'planes.csv'
WITH DELIMITER ',';

COPY Technician (
	id,
	full_name
)
FROM 'technician.csv'
WITH DELIMITER ',';

COPY Flight (
	fnum,
	cost,
	num_sold,
	num_stops,
	actual_departure_date,
	actual_arrival_date,
	arrival_airport,
	departure_airport
)
FROM 'flights.csv'
WITH DELIMITER ',';

COPY Reservation (
	rnum,
	cid,
	fid,
	status
)
FROM 'reservation.csv'
WITH DELIMITER ',';

COPY FlightInfo (
	fiid,
	flight_id,
	pilot_id,
	plane_id
)
FROM 'flightinfo.csv'
WITH DELIMITER ',';

COPY Repairs (
	rid,
	repair_date,
	repair_code,
	pilot_id,
	plane_id,
	technician_id
)
FROM 'repairs.csv'
WITH DELIMITER ',';

COPY Schedule (
	id,
	flightNum,
	departure_time,
	arrival_time
)
FROM 'schedule.csv'
WITH DELIMITER ',';
