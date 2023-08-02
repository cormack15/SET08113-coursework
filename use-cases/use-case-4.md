# USE CASE: 4 Produce a Report on all the Capital Cities in the World Organised by Largest Population to Smallest

## CHARACTERISTIC INFORMATION

### Goal in Context

- **Goal:** Produce a report on all the capital cities in the world organised by largest population to smallest.
- **Actor:** Organisation Member
- **Scope:** Company.
- **Level:** Primary task.
- **Preconditions:** The role of the organization member is known, and the database contains current population data.

### Success End Condition

A report on all the capital cities in the world, organised by largest population to smallest, is available for the organization member to report on.

### Failed End Condition

No report is produced.

### Trigger

A request for capital city population information is sent to the organization.

## MAIN SUCCESS SCENARIO

1. The organization member requests a report on all the capital cities' population information.
2. The organization extracts population information of all capital cities from the database.
3. The organization sorts the capital cities in descending order based on population.
4. The organization generates a report containing the capital cities and their respective population data, organised by largest population to smallest.
5. The organization member receives the report.

## EXTENSIONS

- **Step 2:** If the database does not contain the required population data for all capital cities, the organization informs the member that the report cannot be generated due to insufficient data.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
