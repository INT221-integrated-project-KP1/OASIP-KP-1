<<<<<<< HEAD
=======
use mydb;

set names utf8;

>>>>>>> origin/testAction
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values 
('INFRA-26-11:15','INFRA-26-11:15@kmutt.ac.th',null,'2022-05-26 04:15:00',2,20);
update eventCategory set event_duration = 30 where event_category_id=2;
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values 
('INFRA-26-09:20','INFRA-26-09:20@kmutt.ac.th',null,'2022-05-26 02:20:00',2,30);
update eventCategory set event_duration = 20 where event_category_id=2;
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values 
('INFRA-26-10:40','INFRA-26-10:40@kmutt.ac.th',null,'2022-05-26 03:40:00',2,20);
update eventCategory set event_duration = 30 where event_category_id=2;
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values 
('INFRA-26-12:00','INFRA-26-12:00@kmutt.ac.th',null,'2022-05-26 05:00:00',2,30);

insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values
('DB-26-10:20','DB-26-10:20@kmutt.ac.th',null,'2022-05-26 03:20:00',3,15);

update eventCategory set event_duration = 20 where event_category_id=2;
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values
('INFRA-27-10:20','INFRA-27-10:20@kmutt.ac.th',null,'2022-05-27 03:20:00',2,20);

insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values 
('INFRA-26-10:00','INFRA-26-10:00@kmutt.ac.th',null,'2022-05-26 03:00:00',2,20);

update eventCategory set event_duration = 10 where event_category_id=2;
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values
('INFRA-26-11:45','INFRA-26-11:45@kmutt.ac.th',null,'2022-05-26 04:45:00',2,10);
update eventCategory set event_duration = 20 where event_category_id=2;
