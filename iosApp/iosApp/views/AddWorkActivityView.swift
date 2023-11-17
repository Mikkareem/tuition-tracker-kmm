import SwiftUI

struct AddWorkActivityView: View {
    
    @Environment(\.dismiss) var dismiss
    
    @EnvironmentObject var viewModel: WorkActivityViewModel
    
    @State private var activityName: String = ""
    @State private var activityDescription: String = ""
    @State private var activityGroupKey: String
    @State private var activityGroupValue: String
    @State private var activityCreationDate: Date = .now
    @State private var activityExpirationDate: Date = .now
    
    @State private var showGroupKeySelection: Bool = false
    @State private var groupKeySelectionValue: String = ""
    
    @State private var showGroupValueSelection: Bool = false
    @State private var groupValueSelectionValue: String = ""
    
    @State private var alertTitle: String = ""
    @State private var showAlert: Bool = false
    
    private var isForIndividualActivity: Bool = false
    
    init() {
        _activityGroupKey = State(initialValue: "")
        _activityGroupValue = State(initialValue: "")
    }
    
    init(individualId: Int64) {
        _activityGroupKey = State(initialValue: GroupKeyCategory.individual.rawValue)
        _activityGroupValue = State(initialValue: String(individualId))
        isForIndividualActivity = true
    }
    
    private func getGroupValueSelectionView() -> some View {
        Group {
            if !groupKeySelectionValue.isEmpty {
                if groupKeySelectionValue == GroupKeyCategory.genderWise.rawValue {
                    GroupValueSelectionView(
                        values: ["Male", "Female"],
                        showGroupValueSelection: $showGroupValueSelection,
                        groupValueSelectionValue: $groupValueSelectionValue
                    )
                } else if groupKeySelectionValue == GroupKeyCategory.standardWise.rawValue {
                    GroupValueSelectionView(
                        values: ["1","2","3","4","5","6","7","8","9","10","11","12"],
                        showGroupValueSelection: $showGroupValueSelection,
                        groupValueSelectionValue: $groupValueSelectionValue
                    )
                } else if groupKeySelectionValue == GroupKeyCategory.all.rawValue {
                    GroupValueSelectionView(
                        values: ["ALL"],
                        showGroupValueSelection: $showGroupValueSelection,
                        groupValueSelectionValue: $groupValueSelectionValue
                    )
                }
            }
        }
    }
    
    private func getGroupValueSelectionValue() -> String {
        if groupKeySelectionValue.isEmpty {
            return ""
        }
        
        if groupKeySelectionValue == GroupKeyCategory.genderWise.rawValue {
            return String(groupValueSelectionValue[groupValueSelectionValue.startIndex])
        }
        
        return groupValueSelectionValue
    }
    
    private func isAllFieldsValid() -> Bool {
        if activityName.isEmpty || activityDescription.isEmpty || activityGroupKey.isEmpty || activityGroupValue.isEmpty {
            return false
        }
        
        if ![
            GroupKeyCategory.genderWise.rawValue,
            GroupKeyCategory.individual.rawValue,
            GroupKeyCategory.standardWise.rawValue,
            GroupKeyCategory.all.rawValue
        ].contains(where: { $0 == activityGroupKey }) {
//            return "Please Select Group Key"
            return false
        }
        
        if (
            (activityGroupKey == GroupKeyCategory.genderWise.rawValue && (!["M", "F"].contains(where: { $0 == activityGroupValue }))) ||
            (activityGroupKey == GroupKeyCategory.standardWise.rawValue && (!["1","2","3","4","5","6","7","8","9","10","11","12"].contains(where: { $0 == activityGroupValue }))) ||
            (activityGroupKey == GroupKeyCategory.all.rawValue && activityGroupValue != "ALL")
        ) {
            // Please select appropriate Group value.
            return false
        }
        
        return true
    }
    
    private func getErrorMessage() -> String {
        
        if activityName.isEmpty {
            return "Activity name is mandatory"
        } else if activityDescription.isEmpty {
            return "Activity Description is mandatory"
        } else if activityGroupKey.isEmpty {
            return "Activity Group Key is mandatory"
        } else if activityGroupValue.isEmpty {
            return "Activity Group Value is mandatory"
        }
        
        if ![
            GroupKeyCategory.genderWise.rawValue,
            GroupKeyCategory.individual.rawValue,
            GroupKeyCategory.standardWise.rawValue,
            GroupKeyCategory.all.rawValue
        ].contains(where: { $0 == activityGroupKey }) {
            return "Please Select Group Key"
        }
        
        if (
            (activityGroupKey == GroupKeyCategory.genderWise.rawValue && (!["M", "F"].contains(where: { $0 == activityGroupValue }))) ||
            (activityGroupKey == GroupKeyCategory.standardWise.rawValue && (!["1","2","3","4","5","6","7","8","9","10","11","12"].contains(where: { $0 == activityGroupValue }))) ||
            (activityGroupKey == GroupKeyCategory.all.rawValue && activityGroupValue != "ALL")
        ) {
            return "Please select appropriate Group value"
        }
        
        return ""
    }
    
    private func saveButtonPressed() {
        if !isForIndividualActivity {
            activityGroupKey = groupKeySelectionValue
            activityGroupValue = getGroupValueSelectionValue()
        }
        
        if isAllFieldsValid() {
            viewModel.addWorkActivity(name: activityName, description: activityDescription, groupKey: activityGroupKey, groupValue: activityGroupValue, createdDate: activityCreationDate, expirationDate: activityExpirationDate)
            dismiss()
        } else {
            alertTitle = getErrorMessage()
            showAlert = true
        }
    }
    
    var body: some View {
        VStack(spacing: 20) {
            Text("Create Activity Form")
                .font(.largeTitle)
                .bold()
            TextFieldView("Enter Activity Name", value: $activityName)
            TextFieldView("Enter Activity Description", value: $activityDescription)
            
            Group {
                if isForIndividualActivity {
                    HStack {
                        Text("Group Key")
                        Spacer().frame(width: 30)
                        Text(activityGroupKey)
                    }
                    HStack {
                        Text("Group Value")
                        Spacer().frame(width: 30)
                        Text(activityGroupValue)
                    }
                } else {
                    GroupKeySelectionView(
                        showGroupKeySelection: $showGroupKeySelection,
                        groupKeySelectionValue: $groupKeySelectionValue,
                        groupValueSelectionValue: $groupValueSelectionValue
                    )
                    
                    getGroupValueSelectionView()
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            
            
            DatePicker(selection: $activityCreationDate, in: ...Date.now, displayedComponents: .date) {
                Text("Activity Creation Date")
            }
            
            DatePicker(selection: $activityExpirationDate, in: Date.now..., displayedComponents: .date) {
                Text("Activity Expiration Date")
            }
            Spacer().frame(height: 40)
            Button("Save", action: saveButtonPressed)
            Spacer()
        }
        .padding()
        .alert(alertTitle, isPresented: $showAlert) {
            Button("OK") {}
        }
    }
}
