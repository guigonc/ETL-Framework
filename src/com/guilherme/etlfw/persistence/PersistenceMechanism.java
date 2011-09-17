package com.guilherme.etlfw.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.guilherme.etlfw.mask.registry.RegistryMask;

public class PersistenceMechanism {
	static Connection connector;

	public static void startConnection(String databaseName, String user, String password) throws SQLException {
		connector = (Connection) DriverManager.getConnection(databaseName,
				user, password);
	}

	public static void finishConnection() throws SQLException {
		connector.close();
	}

	public static void insert(@SuppressWarnings("rawtypes") RegistryMask registry)
			throws SQLException {
		createTable(registry);
		applyChanges(registry);
		connector.createStatement().executeUpdate(registry.formatToInsert());
	}

	public static void createTable(@SuppressWarnings("rawtypes") RegistryMask registry)
			throws SQLException {
		String table = registry.formatToCreateTable();
		connector.createStatement().executeUpdate(table);
	}

	public static void applyChanges(@SuppressWarnings("rawtypes") RegistryMask registry)
			throws SQLException {
		String assignments = registry.formatToAlter();
		if (assignments.isEmpty()) {
			return;
		}
		connector.createStatement().executeUpdate(assignments);
		registry.changeAssignmentState(true);
	}

}
