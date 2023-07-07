package com.nt.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetDTO {
	private String assetId;
	private String assetName;
	private String assetModel;
	private String assetType;
	private String assignmentId;
	private String assignedName;
	private String assignedBy;
	private String assignedTo;
	private String assignedOn;
	private String assignedTill;
	private String approveStatus;
	private String lastUpdated;
	private String status;
	private String cost;
	private String categoryType;
	private String assetContract;
}
