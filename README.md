## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

# Hospital Management System

DBMS micro project- Instructions to run

## Installation

Create a new database called hs

```bash
CREATE DATABASE hs
```

Use the source command to run all the sql queries stored in the .sql files

```bash
SOURCE (pathname of create.sql)
SOURCE (pathname of alter.sql)
SOURCE (pathname of insert.sql)
```

## Usage

On VSCode open the project folder. In the filename DAO_Factory.java change the variable PASS to your sql password.

Run the project by by running the driver code which is App.java.
