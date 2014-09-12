/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.performance.test.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.performance.test.database.dominio.Lista;
import edu.performance.test.database.dominio.Nota;
import edu.performance.test.database.dominio.Tarefa;
import edu.performance.test.database.util.TrataData;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class TarefasDbAdapter {

	public static final String CHAVE_IDSERIES = "_idSeries";
	private static final String DATABASE_TB_TAREFAS = "tarefas";
	private static final String DATABASE_TB_TAREFAS_TEMP = "tarefasTemporarias";
	private static final String DATABASE_TB_LISTA = "listas";
	private static final String DATABASE_TB_TAGS = "tags";
	public static final String CHAVE_NOME_LISTA = "nome";
	public static final String CHAVE_ID = "_id";
	public static final String CHAVE_IDNOTA = "_idNota";
	public static final String CHAVE_NAME = "name";
	public static final String CHAVE_LISTA = "list";
	public static final String CHAVE_TAGS = "tag";
	public static final String CHAVE_PRIO = "prioridade";
	public static final String CHAVE_DATAPREVISTA = "data_prevista";
	public static final String CHAVE_HORAPREVISTA = "hora_prevista";
	public static final String CHAVE_TIPO = "tipo";
	public static final String CHAVE_REPETICAO = "repeticao";
	public static final String CHAVE_URL = "url";
	public static final String CHAVE_TITULO = "titulo";
	public static final String CHAVE_NOTA = "nota";
	public static final String CHAVE_AUTH = "auth_token";
	public static final String CHAVE_ATUALIZA_INICIO = "atualizarAoIniciar";
	public static final String CHAVE_CAPA = "capa";
	private static final String TAG = "TarefasDbAdapter";
	public Cursor cnf;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE_TB_TAREFAS = "create table "
			+ DATABASE_TB_TAREFAS
			+ "(_id integer primary key, "
			+ "_idSeries integer not null, name text not null, list text not null, "
			+ CHAVE_PRIO + " text not null, " + CHAVE_DATAPREVISTA + " date, "
			+ CHAVE_HORAPREVISTA + " text, " + CHAVE_REPETICAO + " text, "
			+ CHAVE_URL + " text);";
	private static final String DATABASE_CREATE_TB_LISTAS = "create table "
			+ DATABASE_TB_LISTA + " (" + CHAVE_ID + " integer primary key, "
			+ CHAVE_NOME_LISTA + " text not null);";
	private static final String DATABASE_CREATE_TB_TAGS = "create table "
			+ DATABASE_TB_TAGS + " (" + CHAVE_ID + " integer not null, "
			+ CHAVE_TAGS + " text not null, " + CHAVE_IDSERIES
			+ " integer not null, PRIMARY KEY ( " + CHAVE_ID + ","
			+ CHAVE_IDSERIES + "," + CHAVE_TAGS + "));";

	private static final String DATABASE_NAME = "library_database_test";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private boolean aberto = false;

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		/*
		 * @Override public void onOpen(SQLiteDatabase db) { super.onOpen(db);
		 * 
		 * if (!db.isReadOnly()) { db.execSQL("PRAGMA foreign_keys=ON;"); } }
		 */

		DatabaseHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(DATABASE_CREATE_TB_TAREFAS);
			db.execSQL(DATABASE_CREATE_TB_LISTAS);
			db.execSQL(DATABASE_CREATE_TB_TAGS);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.w(TAG, "Atualizando banco de dados da version: " + oldVersion
					+ " para: " + newVersion
					+ ", isso destruir� todos os dados anteriores");

			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TB_TAREFAS);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TB_LISTA);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TB_TAGS);
			onCreate(db);

		}

	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public TarefasDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	Cursor getConfiguracoesAntigas(SQLiteDatabase bd) {

		return bd.query(false, DATABASE_TB_TAREFAS, new String[] { CHAVE_ID },
				null, null, null, null, null, null);
	}

	public TarefasDbAdapter open() throws SQLException {

		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		setAberto(true);
		cnf = getConfiguracoesAntigas(mDb);

		return this;
	}

	public void close() {
		setAberto(false);
		mDbHelper.close();
	}

	/**
	 * Create a new note using the title and body provided. If the note is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the note
	 * @param body
	 *            the body of the note
	 * @return rowId or -1 if failed
	 */
	public long adicionaTarefa(Long id, String name, Long idSeries,
			String lista, String prio, String limite, String hora,
			String repeticao, String url) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(CHAVE_IDSERIES, idSeries);
		initialValues.put(CHAVE_ID, id);
		initialValues.put(CHAVE_NAME, name);
		initialValues.put(CHAVE_LISTA, lista);
		initialValues.put(CHAVE_PRIO, prio);
		initialValues.put(CHAVE_DATAPREVISTA, limite);
		initialValues.put(CHAVE_HORAPREVISTA, hora);
		initialValues.put(CHAVE_REPETICAO, repeticao);
		initialValues.put(CHAVE_URL, url);
		return mDb.insert(DATABASE_TB_TAREFAS, null, initialValues);
	}

	public long adicionaTags(Long id, String name, Long idSeries) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(CHAVE_IDSERIES, idSeries);
		initialValues.put(CHAVE_ID, id);
		initialValues.put(CHAVE_TAGS, name);

		return mDb.insert(DATABASE_TB_TAGS, null, initialValues);
	}

	public long adicionaTarefaTemporaria(Long id, String name, Long idSeries,
			String lista, String prio, String limite, String hora,
			String repeticao, String url) {
		Long resultado;
		ContentValues initialValues = new ContentValues();
		initialValues.put(CHAVE_IDSERIES, idSeries);
		initialValues.put(CHAVE_ID, id);
		initialValues.put(CHAVE_NAME, name);
		initialValues.put(CHAVE_LISTA, lista);
		initialValues.put(CHAVE_PRIO, prio);
		initialValues.put(CHAVE_DATAPREVISTA, limite);
		initialValues.put(CHAVE_HORAPREVISTA, hora);
		initialValues.put(CHAVE_REPETICAO, repeticao);
		initialValues.put(CHAVE_URL, url);
		Cursor temp = getTarefaTemporariaPelosIds(id, idSeries);
		if (temp == null || temp.isClosed() || temp.getCount() == 0)
			resultado = mDb.insert(DATABASE_TB_TAREFAS_TEMP, null,
					initialValues);
		else
			resultado = Long.valueOf(mDb.update(DATABASE_TB_TAREFAS_TEMP,
					initialValues, CHAVE_IDSERIES + "=" + idSeries + " AND "
							+ CHAVE_ID + "=" + id, null));
		temp.close();
		return resultado;
	}

	public long adicionaListas(Long id, String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(CHAVE_ID, id);
		initialValues.put(CHAVE_NOME_LISTA, name);

		return mDb.insert(DATABASE_TB_LISTA, null, initialValues);
	}

	/**
	 * Delete the note with the given rowId
	 * 
	 * @param rowId
	 *            id of note to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deletaTarefa(Long Id, Long idSeries) {
		String [] args= {Id.toString(), idSeries.toString()};
		return mDb.delete(DATABASE_TB_TAREFAS, CHAVE_ID + "= ? AND "
				+ CHAVE_IDSERIES + "=?" , args) > 0;
	}

	public boolean deletaLista(Long Id) {

		return mDb.delete(DATABASE_TB_LISTA, CHAVE_ID + "=" + Id, null) > 0;
	}

	public boolean deletaTagsPorTarefa(Long Id, Long idSeries) {

		return mDb.delete(DATABASE_TB_TAGS, CHAVE_ID + "=" + Id + " AND "
				+ CHAVE_IDSERIES + "=" + idSeries, null) > 0;
	}

	public boolean deletaTodasTarefas() {

		int a = 0;
		a = mDb.delete(DATABASE_TB_TAREFAS, null, null);

		return a > 0;
	}

	public boolean deletaTodasTarefasTemporarias() {

		int a = 0;
		a = mDb.delete(DATABASE_TB_TAREFAS_TEMP, null, null);

		return a > 0;
	}

	public boolean deletaTodasTags() {

		int a = 0;
		a = mDb.delete(DATABASE_TB_TAGS, null, null);

		return a > 0;
	}

	public boolean deletaTodasListas() {

		int a = 0;
		a = mDb.delete(DATABASE_TB_LISTA, null, null);

		return a > 0;
	}

	/**
	 * Return a Cursor over the list of all notes in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor getTodasTarefas(String ordem, String limit) {

		return mDb.query(DATABASE_TB_TAREFAS, new String[] { CHAVE_ID,
				CHAVE_IDSERIES, CHAVE_NAME, CHAVE_LISTA, CHAVE_PRIO,
				CHAVE_DATAPREVISTA, CHAVE_HORAPREVISTA, CHAVE_REPETICAO,
				CHAVE_URL }, null, null, null, null, CHAVE_PRIO + ","
				+ CHAVE_DATAPREVISTA + "," + CHAVE_HORAPREVISTA, limit);
	}

	public Cursor getTarefasPorLista(String ordem, String lista) {

		return mDb.query(DATABASE_TB_TAREFAS, new String[] { CHAVE_ID,
				CHAVE_IDSERIES, CHAVE_NAME, CHAVE_LISTA, CHAVE_PRIO,
				CHAVE_DATAPREVISTA, CHAVE_HORAPREVISTA, CHAVE_REPETICAO,
				CHAVE_URL }, CHAVE_LISTA + "=" + lista, null, null, null,
				CHAVE_PRIO + "," + CHAVE_DATAPREVISTA + ","
						+ CHAVE_HORAPREVISTA);
	}

	public Cursor getTarefasPorTag(String tag) {

		return mDb.query(DATABASE_TB_TAGS, new String[] { CHAVE_ID,
				CHAVE_IDSERIES }, CHAVE_TAGS + "='" + tag + "'", null, null,
				null, null);
	}

	public Cursor getTarefaPelosIds(Long id, Long idSeries) throws SQLException {

		Cursor mCursor =

		mDb.query(
				true,
				DATABASE_TB_TAREFAS,
				new String[] { CHAVE_IDSERIES, CHAVE_ID, CHAVE_NAME,
						CHAVE_LISTA, CHAVE_DATAPREVISTA, CHAVE_HORAPREVISTA,
						CHAVE_PRIO, CHAVE_REPETICAO, CHAVE_URL },
				CHAVE_IDSERIES + "=" + idSeries + " AND " + CHAVE_ID + "=" + id,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor getTarefaTemporariaPelosIds(Long id, Long idSeries)
			throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TB_TAREFAS_TEMP, new String[] {
				CHAVE_IDSERIES, CHAVE_ID, CHAVE_NAME, CHAVE_LISTA,
				CHAVE_DATAPREVISTA, CHAVE_HORAPREVISTA, CHAVE_PRIO,
				CHAVE_REPETICAO, CHAVE_URL }, CHAVE_IDSERIES + "=" + idSeries
				+ " AND " + CHAVE_ID + "=" + id, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor getTagsPorTarefa(Long id, Long idSeries) throws SQLException {

		return mDb.query(false, DATABASE_TB_TAGS, new String[] { CHAVE_TAGS,
				CHAVE_ID, CHAVE_IDSERIES }, CHAVE_IDSERIES + "=" + idSeries
				+ " AND " + CHAVE_ID + "=" + id, null, null, null, null, null);

	}

	public boolean atualizarTarefa(Long idSeries, Long id, String name,
			String dataPrevista, String lista, String prioridade,
			String horaPrevista, String repeticao, String url) {
		ContentValues args = new ContentValues();
		args.put(CHAVE_NAME, name);
		args.put(CHAVE_DATAPREVISTA, dataPrevista);
		args.put(CHAVE_HORAPREVISTA, horaPrevista);
		args.put(CHAVE_LISTA, lista);
		args.put(CHAVE_PRIO, prioridade);
		args.put(CHAVE_REPETICAO, repeticao);
		args.put(CHAVE_URL, url);
		return mDb.update(DATABASE_TB_TAREFAS, args, CHAVE_IDSERIES + "="
				+ idSeries + " AND " + CHAVE_ID + "=" + id, null) > 0;
	}

	public static ArrayList<String> deCursorParaArrayListTags(Cursor mCursor) {
		ArrayList<String> tags = new ArrayList<String>();

		if (mCursor == null)
			return null;
		for (int i = 0; i < mCursor.getCount(); i++) {
			mCursor.moveToPosition(i);
			// The Cursor is now set to the right position
			tags.add(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_TAGS)));

		}

		return tags;
	}

	public static ArrayList<Lista> deCursorParaArrayListListas(Cursor mCursor) {
		ArrayList<Lista> listas = new ArrayList<Lista>();
		Lista l = null;
		if (mCursor == null)
			return null;
		for (int i = 0; i < mCursor.getCount(); i++) {
			mCursor.moveToPosition(i);
			l = new Lista(Long.parseLong(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_ID))),
					mCursor.getString(mCursor
							.getColumnIndexOrThrow(CHAVE_NOME_LISTA)));
			// The Cursor is now set to the right position
			listas.add(l);

			l = null;

		}

		return listas;
	}

	public static ArrayList<Nota> deCursorParaArrayListNotas(Cursor mCursor) {
		ArrayList<Nota> notas = new ArrayList<Nota>();
		Nota n = null;
		if (mCursor == null)
			return null;
		for (int i = 0; i < mCursor.getCount(); i++) {
			mCursor.moveToPosition(i);
			n = new Nota();
			n.setId(Long.parseLong(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_ID))));
			n.setTitulo(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_TITULO)));
			n.setNota(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_NOTA)));

			notas.add(n);

			n = null;

		}

		return notas;
	}

	public static ArrayList<Tarefa> deCursorParaArrayListTarefas(Cursor mCursor) {
		ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
		Tarefa tmp = null;
		if (mCursor == null)
			return null;
		for (int i = 0; i < mCursor.getCount(); i++) {
			mCursor.moveToPosition(i);
			tmp = new Tarefa();
			// The Cursor is now set to the right position

			tmp.setName(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_NAME)));
			tmp.setId(mCursor.getLong(mCursor.getColumnIndexOrThrow(CHAVE_ID)));
			tmp.setIdSeries(mCursor.getLong(mCursor
					.getColumnIndexOrThrow(CHAVE_IDSERIES)));
			tmp.setLista(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_LISTA)));
			tmp.setPrioridade(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_PRIO)));
			tmp.setDataPrevista(TrataData.trata(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_DATAPREVISTA))));
			tmp.setHoraPrevista(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_HORAPREVISTA)));
			tmp.setRepeticao(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_REPETICAO)));
			tmp.setUrl(mCursor.getString(mCursor
					.getColumnIndexOrThrow(CHAVE_URL)));
			tarefas.add(tmp);
			tmp = null;
		}

		return tarefas;
	}

	public Cursor getTarefasNoIntervalo(String inferior, String superior,
			String ordem) {
		Cursor resultado;

		resultado = mDb.query(DATABASE_TB_TAREFAS, new String[] { CHAVE_ID,
				CHAVE_IDSERIES, CHAVE_NAME, CHAVE_LISTA, CHAVE_PRIO,
				CHAVE_DATAPREVISTA, CHAVE_HORAPREVISTA, CHAVE_REPETICAO,
				CHAVE_URL }, CHAVE_DATAPREVISTA + " BETWEEN '" + inferior
				+ "' AND '" + superior + "'", null, null, null, ordem);

		return resultado;
	}

	public Cursor getTodasListas(String limit) {
		return mDb.query(false, DATABASE_TB_LISTA, new String[] {
				CHAVE_NOME_LISTA, CHAVE_ID }, null, null, null, null, null,
				limit);
	}

	public Cursor getListasExceto(String listaFora) {
		return mDb.query(false, DATABASE_TB_LISTA, new String[] {
				CHAVE_NOME_LISTA, CHAVE_ID }, CHAVE_NOME_LISTA + "<> '"
				+ listaFora + "'", null, null, null, null, null);
	}

}
