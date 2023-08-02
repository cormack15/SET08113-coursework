# USE CASE: 6 Produce a Report on the Number of People Who Speak Chinese from Greatest Number to Smallest, Including the Percentage of the World Population

## CHARACTERISTIC INFORMATION

### Goal in Context

- **Goal:** Produce a report on the number of people who speak Chinese from greatest number to smallest, including the percentage of the world population.
- **Actor:** Organisation Member
- **Scope:** Company.
- **Level:** Primary task.
- **Preconditions:** The role of the organization member is known, and the database contains language and population data.

### Success End Condition

A report on the number of people who speak Chinese from greatest number to smallest, including the percentage of the world population, is available for the organization member to report on.

### Failed End Condition

No report is produced.

### Trigger

A request for the report on the number of people who speak Chinese is sent to the organization.

## MAIN SUCCESS SCENARIO

1. The organization member requests a report on the number of people who speak Chinese.
2. The organization extracts language and population data from the database.
3. The organization calculates the number of people who speak Chinese and the percentage of the world population they represent.
4. The organization sorts the data in descending order based on the number of people who speak Chinese.
5. The organization generates a report containing the countries and their respective population of people who speak Chinese, sorted from greatest number to smallest, including the percentage of the world population.
6. The organization member receives the report.

## EXTENSIONS

- **Step 2:** If the database does not contain the required language or population data, the organization informs the member that the report cannot be generated due to insufficient data.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
