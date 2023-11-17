import SwiftUI
import shared

struct StudentRow: View {
    let student: Student
    
    @EnvironmentObject var viewModel: StudentsViewModel
    
    @State var showDeleteAlert: Bool = false
    @State var alertTitle: String = ""
    
    var backgroundColor: Color {
        student.gender == "F" ? Color.indigo : Color(red: 145/255, green: 197/255, blue: 75/255)
    }
    
    var body: some View {
        HStack {
            Text(student.name)
            Spacer()
            Text(student.gender)
                .foregroundColor(.white)
                .bold()
            Spacer()
                .frame(width: 16)
            Image(systemName: "trash")
                .bold()
                .foregroundColor(.red)
                .onTapGesture {
                    alertTitle = "Are you sure to delete \(student.name)?"
                    showDeleteAlert = true
                }
        }
        .alert(alertTitle, isPresented: $showDeleteAlert, actions: {
            HStack {
                Button("Yes") {
                    viewModel.deleteStudent(student)
                }
                Button("No") {
                    // Nothing to do.
                }
            }
        })
        .padding()
        .background(backgroundColor)
        .clipShape(RoundedRectangle(cornerRadius: 15.0))
        .padding(.horizontal)
    }
}

#Preview {
    StudentRow(student: Student(id: 43, name: "Kareem", age: 23, gender: "F", standard: "6", remarks: nil))
}
