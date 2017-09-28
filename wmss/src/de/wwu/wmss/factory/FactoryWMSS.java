package de.wwu.wmss.factory;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import de.wwu.wmss.connectors.PostgreSQLConnector;
import de.wwu.wmss.core.DataSource;
import de.wwu.wmss.core.Format;
import de.wwu.wmss.core.Movement;
import de.wwu.wmss.core.MusicScore;
import de.wwu.wmss.core.PerformanceMedium;
import de.wwu.wmss.core.Person;
import de.wwu.wmss.core.RequestParameter;

public class FactoryWMSS {

	private static Logger logger = Logger.getLogger("Factory-WMSS");

	public static ArrayList<MusicScore> getScoreList(ArrayList<RequestParameter> parameters, DataSource dataSource){


		//TODO add where clause statements


		ArrayList<MusicScore> scoreList = new ArrayList<MusicScore>();
		ArrayList<Movement> movementList = new ArrayList<Movement>();
		ArrayList<PerformanceMedium> mediumList = new ArrayList<PerformanceMedium>();
		ArrayList<Person> personList = new ArrayList<Person>();

		try {

			if (dataSource.getStorage().equals("postgresql")){

				String SQL = "SELECT grp.*,	scr.*, per.*, rol.*, doctype.*, med.*, movmed.*,  medtype.*, mov.* " + 	
						"FROM wmss_scores scr " +
						"JOIN wmss_score_movements mov ON scr.score_id = mov.score_id " + 
						"JOIN wmss_movement_performance_medium movmed ON mov.movement_id = movmed.movement_id AND movmed.score_id = scr.score_id " + 
						"JOIN wmss_performance_medium med ON movmed.performance_medium_id = med.performance_medium_id " +
						"JOIN wmss_performance_medium_type medtype ON med.performance_medium_type_id = medtype.performance_medium_type_id " +
						"JOIN wmss_score_persons scrper ON scrper.score_id = scr.score_id  " +
						"JOIN wmss_persons per ON per.person_id = scrper.person_id  " +
						"JOIN wmss_roles rol ON rol.role_id = scrper.role_id " +
						"JOIN wmss_groups grp ON grp.group_id = scr.group_id " +
						"JOIN wmss_document doc ON doc.score_id = scr.score_id " +
						"JOIN wmss_document_type doctype ON doctype.document_type_id = doc.document_type_id "
						+ " --where scr.score_id in (15, 16)";	

				ResultSet rs = PostgreSQLConnector.executeQuery(SQL, dataSource);


				while (rs.next()){

					boolean scoreAdded = false;

					for (int j = 0; j < scoreList.size(); j++) {

						if(scoreList.get(j).getScoreIdentifier() == rs.getInt("score_id")){
							scoreAdded = true;
						}

					}

					MusicScore rec = new MusicScore();

					rec.setScoreIdentifier(rs.getInt("score_id"));
					rec.setTitle(rs.getString("score_name"));;
					rec.setTonalityMode(rs.getString("score_tonality_mode"));;
					rec.setTonalityTonic(rs.getString("score_tonality_note"));;
					rec.setGroupId(rs.getString("group_id"));
					rec.setGroupDescription(rs.getString("group_description"));

					Movement mov = new Movement();					
					mov.setMovementIdentifier(rs.getInt("movement_id"));
					mov.setTitle(rs.getString("score_movement_description"));
					mov.setTempo(rs.getString("movement_tempo"));
					mov.setScoreId(rs.getInt("score_id"));

					movementList.add(mov);

					PerformanceMedium med = new PerformanceMedium();
					med.setMediumClassification(rs.getString("performance_medium_id"));
					med.setMediumDescription(rs.getString("performance_medium_description"));
					med.setTypeDescription(rs.getString("performance_medium_type_description"));
					med.setMovementId(rs.getInt("movement_id"));
					med.setScoreId(rs.getInt("score_id"));
					med.setSolo(rs.getBoolean("movement_performance_medium_solo"));
					med.setMediumScoreDescription(rs.getString("movement_performance_medium_description"));

					mediumList.add(med);

					Person per = new Person();					
					per.setName(rs.getString("person_name"));
					per.setRole(rs.getString("role_description"));
					per.setScoreId(rs.getInt("score_id"));

					personList.add(per);
					
					Format frm = new Format();
					
					frm.setFormatId(rs.getInt("document_type_id"));
					frm.setFormatDescription(rs.getString("document_type_description"));

					if(scoreAdded == false){

						scoreList.add(rec);

					} 

				}

				/**
				 * Selecting and adding movements that belong to a music score
				 */

				for (int i = 0; i < scoreList.size(); i++) {

					for (int j = 0; j < movementList.size(); j++) {						

						if(scoreList.get(i).getScoreIdentifier() == movementList.get(j).getScoreId()){

							boolean movementAdded = false;

							for (int k = 0; k < scoreList.get(i).getMovements().size(); k++) {

								if(scoreList.get(i).getMovements().get(k).getScoreId() == movementList.get(j).getScoreId() &&
										scoreList.get(i).getMovements().get(k).getTitle().equals(movementList.get(j).getTitle())){

									movementAdded = true;
								}

							}

							if(!movementAdded) {

								scoreList.get(i).getMovements().add(movementList.get(j));

							}

						}

					}


				}


				/**
				 * Selecting and adding performance medium that belong to a movement
				 */
				for (int i = 0; i < scoreList.size(); i++) {

					for (int j = 0; j < scoreList.get(i).getMovements().size(); j++) {

						for (int k = 0; k < mediumList.size(); k++) {

							boolean mediumAdded = false;

							if(scoreList.get(i).getScoreIdentifier() == mediumList.get(k).getScoreId()  &&
							   scoreList.get(i).getMovements().get(j).getMovementIdentifier() == mediumList.get(k).getMovementId()){

								for (int l = 0; l < scoreList.get(i).getMovements().get(j).getPerformanceMediumList().size(); l++) {

									if(scoreList.get(i).getMovements().get(j).getPerformanceMediumList().get(l).getScoreId() == mediumList.get(k).getScoreId() &&
									   scoreList.get(i).getMovements().get(j).getPerformanceMediumList().get(l).getMovementId() == mediumList.get(k).getMovementId() &&
									   scoreList.get(i).getMovements().get(j).getPerformanceMediumList().get(l).getMediumScoreDescription().equals(mediumList.get(k).getMediumScoreDescription())){

										mediumAdded = true;

									} 

								}

								if(!mediumAdded) {scoreList.get(i).getMovements().get(j).getPerformanceMediumList().add(mediumList.get(k));}


							}
						}



					}

				}


				/**
				 * Selecting and adding persons that belong to a music score
				 */

				for (int i = 0; i < scoreList.size(); i++) {


					for (int j = 0; j < personList.size(); j++) {

						boolean personAdded = false;

						for (int k = 0; k < scoreList.get(i).getPersons().size(); k++) {

							if(scoreList.get(i).getPersons().get(k).getName().equals(personList.get(j).getName()) &&
							   scoreList.get(i).getPersons().get(k).getScoreId() == personList.get(j).getScoreId()){

								personAdded = true;

							}

						}

						if(scoreList.get(i).getScoreIdentifier() == personList.get(j).getScoreId()){

							if(!personAdded){	
								
								scoreList.get(i).getPersons().add(personList.get(j));
								
							}

						}

					}


				}




				rs.close();

			}



		} catch (Exception e) {

			//logger.err(e.printStackTrace());
			e.printStackTrace();
		}


		if (dataSource.getStorage().equals("graphdb")){



		}



		return scoreList;
	}

}
