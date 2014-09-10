package edu.performance.test.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.database.dominio.Lista;
import edu.performance.test.database.dominio.Tarefa;
import edu.performance.test.database.util.LoadData;
import edu.performance.test.database.util.TrataData;

/**
 * This class extends PerformanceTest and it executes operations with database
 * SQLite. Currently this operations are - insertion - select - delete - update
 * 
 * @author Thiago
 */
public class DatabaseOperation extends PerformanceTest<Integer>  implements PerformanceTestInterface {

	private TarefasDbAdapter mDbHelper;

	/**
	 * Initializes the DBAdapter with application context and makes possible get
	 * application references.
	 * 
	 * @param activity
	 */
	public DatabaseOperation(PerformanceTestActivity activity, int level) {
		super(level, activity);
		
		mDbHelper = new TarefasDbAdapter(activity.getApplicationContext());
		activity.executeTest();
	}

	/**
	 * This method calls methods to perform operation of insertion(Lists and
	 * Tasks), selection (Lists and Tasks), update (only tasks) and delete
	 * (Lists and Tasks). Here the level determines the number of operations
	 * which will be executed.
	 */
	public void execute() {
		mDbHelper.open();
		testTpJMaddLists(this.getLevel());
		testTpJMaddTasks(this.getLevel());
		Cursor tasks = testTpJMgetTasks(String.valueOf(this.getLevel()));
		Cursor lists = testTpJMgetLists(String.valueOf(this.getLevel()));
		testTpJMupdateTasks(tasks);
		testTpJMdeleteLists(lists);
		testTpJMdeleteTasks(tasks);
		mDbHelper.close();

		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

	/**
	 * This method performs the operation of Lists insertion. The number of
	 * insertions is defined by level.
	 * 
	 * @param level
	 *            Determines how many operations will be executed
	 */
	private void testTpJMaddLists(int level) {
		/**
		 * Level will determinate how many operations will be executed
		 */

		ArrayList<Lista> allLists = new LoadData().getLists(activity);
		
		if (allLists != null) {
			if (!mDbHelper.isAberto()) {
				mDbHelper.open();
			}
			mDbHelper.deletaTodasListas();
			level = level <= allLists.size() ? level : allLists.size();
			//System.out.println("num Listas: " + level);
			Lista l = null;
			for (int i = 0; i < level; i++) {
				l = allLists.get(i);
				mDbHelper.adicionaListas(l.getId(), l.getNome());
				l = null;
			}
		}
	}

	/**
	 * This method performs the operation of Lists selection. The number of
	 * selected lines is defined by level.
	 * 
	 * @param level
	 *            Determines how many operations will be executed
	 */
	private Cursor testTpJMgetTasks(String level) {

		if (!mDbHelper.isAberto()) {
			mDbHelper.open();
		}
		return mDbHelper.getTodasTarefas("", level);

	}

	/**
	 * This method performs the operation of Lists selection. The number of
	 * selected lines is defined by level.
	 * 
	 * @param level
	 *            Determines how many operations will be executed
	 */
	private Cursor testTpJMgetLists(String level) {

		if (!mDbHelper.isAberto()) {
			mDbHelper.open();
		}
		return mDbHelper.getTodasListas(level);

	}

	/**
	 * This method performs the operation of Tasks insertion. The number of
	 * insertions is defined by level.
	 * 
	 * @param level
	 *            Determines how many operations will be executed
	 */
	private void testTpJMaddTasks(int level) {
		/**
		 * Level will determinate how many operations will be executed
		 */

		ArrayList<Tarefa> todasTarefas = new LoadData().getTasks(activity);

		if (todasTarefas != null) {
			if (!mDbHelper.isAberto()) {
				mDbHelper.open();
			}
			mDbHelper.deletaTodasTarefas();
			mDbHelper.deletaTodasTags();

			level = level <= todasTarefas.size() ? level : todasTarefas.size();
			System.out.println("num Tarefas: " + level);
			Tarefa t = null;
			for (int i = 0; i < level; i++) {
				t = todasTarefas.get(i);
				mDbHelper.adicionaTarefa(t.getId(), t.getName(),
						t.getIdSeries(), t.getLista(), t.getPrioridade(),
						TrataData.trataGregorian(t.getDataPrevista()),
						t.getHoraPrevista(), t.getRepeticao(), t.getUrl());

				for (String tg : t.getTags()) {

					mDbHelper.adicionaTags(t.getId(), tg, t.getIdSeries());
				}

				t = null;
			}

		}

	}

	/**
	 * This method performs the operation of tasks deletion.The number of
	 * deletions is defined by level.
	 * 
	 * @param mCursor
	 *            This parameter contain all tasks added in database
	 */
	private void testTpJMdeleteTasks(Cursor mCursor) {
		if (mCursor == null)
			return;

		int limit = this.getLevel() < mCursor.getCount() ? this.getLevel()
				: mCursor.getCount();

		for (int i = 0; i < limit; i++) {
			mCursor.moveToPosition(i);
			mDbHelper
					.deletaTarefa(
							mCursor.getLong(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_ID)),
							mCursor.getLong(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_IDSERIES)));

		}

	}

	/**
	 * This method performs the operation of lists deletion. The number of
	 * deletions is defined by level.
	 * 
	 * @param mCursor
	 *            This parameter contain all lists added in database
	 */
	private void testTpJMdeleteLists(Cursor mCursor) {
		if (mCursor == null)
			return;

		int limit = this.getLevel() < mCursor.getCount() ? this.getLevel()
				: mCursor.getCount();

		for (int i = 0; i < limit; i++) {
			mCursor.moveToPosition(i);
			mDbHelper.deletaLista(mCursor.getLong(mCursor
					.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_ID)));

		}
	}

	/**
	 * This method performs the operation of lists update. The number of updated
	 * lists is defined by level.
	 * 
	 * @param mCursor
	 *            This parameter contain all lists added in database
	 */
	private void testTpJMupdateTasks(Cursor mCursor) {
		if (mCursor == null)
			return;

		int limit = this.getLevel() < mCursor.getCount() ? this.getLevel()
				: mCursor.getCount();

		for (int i = 0; i < limit; i++) {
			mCursor.moveToPosition(i);
			mDbHelper
					.atualizarTarefa(
							mCursor.getLong(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_IDSERIES)),
							mCursor.getLong(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_ID)),
							mCursor.getString(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_NAME))
									+ "/n new text in name.",
							"01/01/2016",
							mCursor.getString(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_LISTA)),
							"1",
							"08:37",
							mCursor.getString(mCursor
									.getColumnIndexOrThrow(TarefasDbAdapter.CHAVE_REPETICAO)),
							"www.inf.ufrgs.br");

		}

	}

}
