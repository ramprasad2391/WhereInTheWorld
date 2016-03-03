package com.example.whereintheworld;

public class Question {
	private int questionId;
	private String questionType;
	private String continent;
	private String country;
	private String question;
	private String answer;
	
	public Question(){
		
	}
	
	
	
	public Question(int questionId, String question, String answer) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.answer = answer;
	}



	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the questionType
	 */
	public String getQuestionType() {
		return questionType;
	}
	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	/**
	 * @return the continent
	 */
	public String getContinent() {
		return continent;
	}
	/**
	 * @param continent the continent to set
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionType="
				+ questionType + ", continent=" + continent + ", country="
				+ country + ", question=" + question + ", answer=" + answer
				+ "]";
	}
	
	

}
