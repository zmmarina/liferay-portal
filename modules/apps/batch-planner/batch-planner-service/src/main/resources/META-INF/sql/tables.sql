create table BatchPlannerLog (
	mvccVersion LONG default 0 not null,
	batchPlannerLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	size_ INTEGER,
	total INTEGER,
	status INTEGER
);

create table BatchPlannerMapping (
	mvccVersion LONG default 0 not null,
	batchPlannerMappingId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG
);

create table BatchPlannerPlan (
	mvccVersion LONG default 0 not null,
	batchPlannerPlanId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	name VARCHAR(75) null,
	export BOOLEAN
);

create table BatchPlannerPolicy (
	mvccVersion LONG default 0 not null,
	batchPlannerPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	name VARCHAR(75) null,
	value VARCHAR(75) null
);