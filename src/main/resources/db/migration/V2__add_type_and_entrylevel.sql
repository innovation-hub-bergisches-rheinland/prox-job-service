drop table if exists job_offer_available_types CASCADE;
drop table if exists job_offer_entry_levels CASCADE;
drop table if exists job_offer_entry_level CASCADE;
drop table if exists job_offer_type CASCADE;

create table job_offer_available_types (job_offer_id uuid not null, available_types_id uuid not null, primary key (job_offer_id, available_types_id));
create table job_offer_entry_levels (job_offer_id uuid not null, entry_levels_id uuid not null, primary key (job_offer_id, entry_levels_id));

create table job_offer_entry_level (id uuid not null, description varchar(255), entry_level integer not null, primary key (id));
create table job_offer_type (id uuid not null, description varchar(255), type integer not null, primary key (id));

alter table job_offer_available_types add constraint UK_c4ixoj0lg9xgjso43kxe1gmqj unique (available_types_id);
alter table job_offer_entry_levels add constraint UK_r65wuh80xuuxnbn68jl23hkwe unique (entry_levels_id);
alter table job_offer_available_types add constraint FKmnfh0h7gfnduvkcdhbt6yaw3q foreign key (available_types_id) references job_offer_type;
alter table job_offer_available_types add constraint FKm2n32ieqbwobx2ay6ualvy9xu foreign key (job_offer_id) references job_offer;
alter table job_offer_entry_levels add constraint FKamnkvrsthfvt9692uxxdow1nr foreign key (entry_levels_id) references job_offer_entry_level;
alter table job_offer_entry_levels add constraint FKr6kjh49h5368cojf2iueptv26 foreign key (job_offer_id) references job_offer;

insert into job_offer_entry_level values ('3f48ae4a-7188-4825-ad5c-8693d9fc68cd', 'Berufserfahrene', 1);
insert into job_offer_entry_level values ('bf1c1537-5baa-4024-be6a-20a7b6a07ed2', 'Berufseinsteiger', 0);

INSERT into job_offer_type values ('bf1c1537-5baa-4024-be6a-20a7b6a07ed2', 'Vollzeit', 0);
INSERT into job_offer_type values ('efef8360-fa17-4deb-be80-a48eebef1b8c', 'Teilzeit', 1);
INSERT into job_offer_type values ('8f488770-48c4-4980-92a3-5ad44af0c373', 'Ausbildung / Praktikum', 2);
INSERT into job_offer_type values ('4e43370d-eee2-4c80-8da1-1349edc3f63d', 'Minijob', 3);
INSERT into job_offer_type values ('a42c9804-8840-405f-b073-d8e9438a0488', 'Studentische Hilfskraft / Werkstudent', 4);
INSERT into job_offer_type values ('d8518b79-d7fe-4158-a1bd-7950811ae6fc', 'Wissenschaftliche Hilfskraft', 5);
