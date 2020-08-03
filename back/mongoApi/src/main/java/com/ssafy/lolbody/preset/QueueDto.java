package com.ssafy.lolbody.preset;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "queues")
public class QueueDto {
	@Id
	private String queueId;
	private String map;
	private String description;
	private String notes;

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "QueueDto [queueId=" + queueId + ", map=" + map + ", description=" + description + ", notes=" + notes
				+ "]";
	}
}
