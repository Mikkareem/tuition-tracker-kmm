import SwiftUI

struct AddStudentNavLink: View {
    
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    var body: some View {
//        NavigationLink(value: StudentsNavigation.addStudent) {
//            Text("Add Student")
//        }

        Button("Add Student") {
            appNavigation.addStudentPath(CustomNavigation.addStudent)
        }
    }
}
