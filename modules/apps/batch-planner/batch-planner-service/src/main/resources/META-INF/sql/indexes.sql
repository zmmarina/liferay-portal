create index IX_4E2BAD82 on BatchPlannerLog (batchPlannerPlanId);

create index IX_F08FB8C on BatchPlannerMapping (batchPlannerPlanId);

create unique index IX_221A54A0 on BatchPlannerPlan (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_874FA8DB on BatchPlannerPlan (companyId, userId);

create unique index IX_A8E0209F on BatchPlannerPolicy (batchPlannerPlanId, name[$COLUMN_LENGTH:75$]);