# USE CASE: 5 Produce a Report on the Population of People, People Living in Cities, and People Not Living in Cities in Each Country

## CHARACTERISTIC INFORMATION

### Goal in Context

- **Goal:** Produce a report on the population of people, people living in cities, and people not living in cities in each country.
- **Actor:** Organisation Member
- **Scope:** Company.
- **Level:** Primary task.
- **Preconditions:** The role of the organization member is known, and the database contains population data.

### Success End Condition

A report on the population of people, people living in cities, and people not living in cities in each country is available for the organization member to report on.

### Failed End Condition

No report is produced.

### Trigger

A request for the report on the population data is sent to the organization.

## MAIN SUCCESS SCENARIO

1. The organization member requests a report on the population of people, people living in cities, and people not living in cities in each country.
2. The organization extracts population data from the database for each country.
3. The organization calculates the population of people, people living in cities, and people not living in cities for each country.
4. The organization generates a report containing the countries and their respective population data, including the population of people, people living in cities, and people not living in cities.
5. The organization member receives the report.

## EXTENSIONS

- **Step 2:** If the database does not contain the required population data for all countries, the organization informs the member that the report cannot be generated due to insufficient data.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
