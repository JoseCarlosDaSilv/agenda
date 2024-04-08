package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ContatoDao.
 */
public class ContatoDao {

	/**
	 * Select all contato.
	 *
	 * @return the array list
	 */
	public ArrayList<Contato> selectAllContato() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idcon, nome, fone, email ");
		sql.append("FROM contatos ");
		try {
			Connection connection = ModuloConnection.conectar();
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			// ResultSet variavel do java que guarda resultSet de consulta
			ResultSet resultSet = preparedStatement.executeQuery();
			// resultSet perde os dados qdo conexao é fechada entao
			// o resultSet é guardado no ArrayList
			ArrayList<Contato> arrayList = new ArrayList<>();
			while (resultSet.next()) {
				Contato contato = new Contato();
				contato.setIdcon(resultSet.getInt("idcon"));
				contato.setNome(resultSet.getString("nome"));
				contato.setFone(resultSet.getString("fone"));
				contato.setEmail(resultSet.getString("email"));
				arrayList.add(contato);
			}
			connection.close();
			return arrayList;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Select contato by id.
	 *
	 * @param contato the contato
	 */
	public void selectContatoById(Contato contato) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idcon, nome,fone, email ");
		sql.append("FROM contatos ");
		sql.append("where idcon = ?");
		try {
			Connection connection = ModuloConnection.conectar();
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, contato.getIdcon());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				contato.setIdcon(resultSet.getInt("idcon"));
				contato.setNome(resultSet.getString("nome"));
				contato.setFone(resultSet.getString("fone"));
				contato.setEmail(resultSet.getString("email"));
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Insert contato.
	 *
	 * @param contato the contato
	 */
	public void insertContato(Contato contato) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into contatos(nome, fone, email) values ");
		sql.append("(?,?,?)");
		try {
			Connection connection = ModuloConnection.conectar();
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, contato.getNome());
			preparedStatement.setString(2, contato.getFone());
			preparedStatement.setString(3, contato.getEmail());
			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Update contato.
	 *
	 * @param contato the contato
	 */
	public void updateContato(Contato contato){
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE contatos ");
		sql.append("SET ");
		sql.append("nome = ?, ");
		sql.append("fone = ?, ");
		sql.append("email = ? ");
		sql.append("WHERE idcon = ? ");
		try {
			Connection connection = ModuloConnection.conectar();
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, contato.getNome());
			preparedStatement.setString(2, contato.getFone());
			preparedStatement.setString(3, contato.getEmail());
			preparedStatement.setInt(4, contato.getIdcon());
			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Delete contato.
	 *
	 * @param contato the contato
	 */
	public void deleteContato(Contato contato) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from contatos ");
		sql.append("WHERE idcon = ? ");
		try {
			Connection connection = ModuloConnection.conectar();
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, contato.getIdcon());
			preparedStatement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
