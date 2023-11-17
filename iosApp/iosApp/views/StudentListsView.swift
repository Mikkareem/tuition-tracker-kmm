import SwiftUI
import shared

struct StudentListsView: View {
    
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    @EnvironmentObject private var viewModel: StudentsViewModel
    
	var body: some View {
        VStack {
            Text("Total Students: \(viewModel.studentNames.count)")
                .font(.headline)
                .foregroundColor(.blue)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal)
            ScrollView {
                ForEach(viewModel.studentNames, id: \.id) { student in
//                    NavigationLink(value: student) {
//                        StudentRow(student: student)
//                            .foregroundColor(.black)
//                    }
                    
                    StudentRow(student: student)
                        .foregroundColor(.black)
                        .onTapGesture {
                            appNavigation.addStudentPath(student)
                        }
                }
            }
            .navigationTitle("My Students")
            .navigationBarItems(trailing: AddStudentNavLink())
        }
        .task {
            await viewModel.loadStudentNames()
        }
	}
}

struct StudentListsView_Previews: PreviewProvider {
    static let databaseApi = DatabaseApi(databaseDriverFactory: DatabaseDriverFactory())
    
    static var studentsRepository: StudentsRepository {
        databaseApi.studentsRepository
    }
    
	static var previews: some View {
        StudentListsView()
            .environmentObject(StudentsViewModel(studentsRepository: studentsRepository))
	}
}
