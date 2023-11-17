//
//  AddWorkActivityExtensions.swift
//  iosApp
//
//  Created by Irsath Kareem on 13/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

extension AddWorkActivityView {
    
    struct GroupKeySelectionView: View {
        
        var defaults: [GroupKeyCategory: String] = [
            .genderWise: "Male",
            .individual: "",
            .standardWise: "1",
            .all: "ALL"
        ]
        
        @Binding var showGroupKeySelection: Bool
        @Binding var groupKeySelectionValue: String
        @Binding var groupValueSelectionValue: String
        
        var body: some View {
            HStack {
                Text("Group Key: ")
                Spacer().frame(width: 20)
                Text(groupKeySelectionValue.isEmpty ? "Select" : groupKeySelectionValue)
                    .foregroundColor(.blue)
                    .actionSheet(isPresented: $showGroupKeySelection) {
                        ActionSheet(
                            title: Text("Select Group Key"),
                            buttons: [
                                .default(Text(GroupKeyCategory.genderWise.rawValue)) {
                                    groupKeySelectionValue = GroupKeyCategory.genderWise.rawValue
                                    groupValueSelectionValue = defaults[.genderWise]!
                                },
                                .default(Text(GroupKeyCategory.standardWise.rawValue)) {
                                    groupKeySelectionValue = GroupKeyCategory.standardWise.rawValue
                                    groupValueSelectionValue = defaults[.standardWise]!
                                },
                                .default(Text(GroupKeyCategory.all.rawValue)) {
                                    groupKeySelectionValue = GroupKeyCategory.all.rawValue
                                    groupValueSelectionValue = defaults[.all]!
                                },
                                .destructive(Text("None")) {
                                    groupKeySelectionValue = ""
                                }
                            ]
                        )
                    }
                    .onTapGesture {
                        showGroupKeySelection = true
                    }
            }
        }
    }
}


extension AddWorkActivityView {
    struct GroupValueSelectionView: View {
        @State var values: [String]
        
        @Binding var showGroupValueSelection: Bool
        @Binding var groupValueSelectionValue: String
        
        var body: some View {
            HStack {
                Text("Group Value: ")
                Spacer().frame(width: 20)
                Text(groupValueSelectionValue.isEmpty ? "Select" : groupValueSelectionValue)
                    .foregroundColor(.blue)
                    .actionSheet(isPresented: $showGroupValueSelection) {
                        ActionSheet(
                            title: Text("Select Group Value"),
                            buttons: $groupValueSelectionValue.getActionButtons(values)
                        )
                    }
                    .onTapGesture {
                        showGroupValueSelection = true
                    }
            }
        }
    }
}
