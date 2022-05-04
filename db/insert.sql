use mydb;
insert into eventCategory values (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้อง เพื่อแสดงระหว่างขอคำปรึกษา',30);
insert into eventCategory values (2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',30);
insert into eventCategory values (3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);
insert into eventCategory values (4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);
insert into eventCategory values (5,'Server-side Clinic',null,30);


update eventCategory set event_duration = 20 where event_category_id=2;