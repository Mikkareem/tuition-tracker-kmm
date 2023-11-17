import SwiftUI
import shared

struct StudentDetailsView: View {
    
    let student: Student
    
    @EnvironmentObject private var viewModel: StudentDetailsViewModel
    
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    private var genderText: String {
        return student.gender == "M" ? "Male" : "Female"
    }
    
    var body: some View {
        VStack {
            Text(student.name)
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(.system(size: 40))
                .foregroundColor(.blue)
                .bold()
            Text("Age: \(student.age)")
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(.title2)
            Text("Gender: \(genderText)")
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(.title2)
            Text("Standard: \(student.standard)")
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(.title2)
            
            Spacer().frame(height: 30)
            
            Section {
                ScrollView {
                    ForEach(viewModel.assignedActivities, id: \.workActivity.id) { activity in
                        HStack {
                            Text("\(activity.workActivity.name)")
                            Spacer()
                            
                            Group {
                                if activity.isCompleted {
                                    Image(systemName: "checkmark.seal.fill")
                                        .font(.title)
                                        .foregroundColor(.green)
                                } else {
                                    Text("Complete")
                                        .foregroundColor(.blue)
                                }
                            }
                            .onTapGesture {
                                viewModel.toggleCompletionStatusOfAssignedActivity(activity)
                            }
                        }
                        .frame(height: 40)
                        .frame(maxWidth: .infinity)
                        
                        Divider()
                    }
                }
            } header: {
                HStack {
                    Text("Assigned Activities")
                        .bold()
                        .font(.system(size: 25))
                    Spacer()
                    Image(systemName: "plus.app")
                        .foregroundColor(.red)
                        .font(.title)
                        .onTapGesture {
                            appNavigation.addStudentPath(student.id)
                        }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .task {
                await viewModel.loadStudentAssignedActivities(student: student)
            }

            
            Spacer()
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding()
    }
}
