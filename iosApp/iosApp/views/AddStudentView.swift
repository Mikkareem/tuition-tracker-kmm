import SwiftUI

struct AddStudentView: View {
    @Environment(\.dismiss) var dismiss
    
    @EnvironmentObject var viewModel: StudentsViewModel
    
    @State private var studentName: String = ""
    @State private var studentAge: String = ""
    @State private var studentGender: String = ""
    @State private var studentStandard: String = ""
    
    @State private var alertTitle: String = ""
    @State private var showAlert: Bool = false
    
    @State private var showGenderSelection: Bool = false
    @State private var genderSelectionValue: String = ""
    
    private func clearFields() {
        self.studentName = ""
        self.studentAge = ""
        self.studentGender = ""
        self.alertTitle = ""
        self.genderSelectionValue = ""
    }
    
    private func isAllFieldsValid() -> Bool {
        if self.studentName.isEmpty || self.studentAge.isEmpty || self.studentGender.isEmpty || self.studentStandard.isEmpty {
            return false
        }
        
        guard let _ = Int32(self.studentAge, radix: 10) else {
            return false
        }
        
        if genderSelectionValue != "Male" && genderSelectionValue != "Female" {
            return false
        }
        
        return true
    }
    
    private func getAlertTitle() -> String {
        if self.studentName.isEmpty {
            return "Student name is mandatory"
        } else if self.studentAge.isEmpty {
            return "Student age is mandatory"
        } else if self.studentGender.isEmpty {
            return "Student Gender is mandatory"
        } else if self.studentStandard.isEmpty {
            return "Student Standard is mandatory"
        }
        
        guard let _ = Int32(self.studentAge, radix: 10) else {
            return "Student age must be a number"
        }
        
        if genderSelectionValue != "Male" && genderSelectionValue != "Female" {
            return "Please Select Student's Gender"
        }
        
        return ""
    }
    
    private func saveButtonPressed() {
        studentGender = genderSelectionValue.getFirstCharacterAsString()
        if isAllFieldsValid() {
            viewModel.addStudent(
                name: studentName,
                age: studentAge,
                gender: studentGender,
                standard: studentStandard
            )
            dismiss()
        } else {
            alertTitle = getAlertTitle()
            showAlert = true
        }
    }
    
    var body: some View {
        VStack {
            TextFieldView("Enter Student Name", value: $studentName)
            TextFieldView("Enter Student Age", value: $studentAge)
            TextFieldView("Enter Student Standard", value: $studentStandard)
            
            GenderSelectionView(
                showGenderSelection: $showGenderSelection,
                genderSelectionValue: $genderSelectionValue
            )
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding()
            
            Button("Save",action: saveButtonPressed)
        }
        .padding()
        .alert(alertTitle, isPresented: $showAlert) {
            Button("OK") {}
        }
    }
}

extension AddStudentView {
    struct GenderSelectionView: View {
        
        @Binding var showGenderSelection: Bool
        @Binding var genderSelectionValue: String
        
        var body: some View {
            HStack {
                Text("Gender: ")
                Spacer().frame(width: 20)
                Text(genderSelectionValue.isEmpty ? "Select" : genderSelectionValue)
                    .foregroundColor(.blue)
                    .actionSheet(isPresented: $showGenderSelection) {
                        ActionSheet(
                            title: Text("Select Gender"),
                            buttons: [
                                .default(Text("Male")) {
                                    genderSelectionValue = "Male"
                                },
                                .default(Text("Female")) {
                                    genderSelectionValue = "Female"
                                }
                            ]
                        )
                    }
                    .onTapGesture {
                        showGenderSelection = true
                    }
            }
        }
    }
}
