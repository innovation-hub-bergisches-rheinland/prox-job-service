create table job_offer (
                           id uuid not null,
                           created_at timestamp,
                           user_id uuid,
                           variant integer,
                           description text not null,
                           title varchar(255) not null,
                           primary key (id)
);

create table job_offer_available_types (
                                           job_offer_id uuid not null,
                                           available_types integer
);

create table job_offer_entry_levels (
                                        job_offer_id uuid not null,
                                        entry_levels integer
);

alter table job_offer_available_types
    add constraint FKm2n32ieqbwobx2ay6ualvy9xu
        foreign key (job_offer_id)
            references job_offer;

alter table job_offer_entry_levels
    add constraint FKr6kjh49h5368cojf2iueptv26
        foreign key (job_offer_id)
            references job_offer;