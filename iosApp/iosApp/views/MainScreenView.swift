import SwiftUI
import shared

struct MainScreenView: View {
    
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    @State private var selectedTabIndex: Int = 0
    
    private var handler: Binding<Int> {
        Binding(
            get: {
                self.selectedTabIndex
            },
            set: {
                if $0 == selectedTabIndex {
                    return
                }
                
                if $0 == 0 {
                    appNavigation.clearActivitiesTabPaths()
                } else if $0 == 1 {
                    appNavigation.clearStudentsTabPaths()
                }
                
                selectedTabIndex = $0
            }
        )
    }
    
    var body: some View {
        TabView(selection: handler) {
            NavigationStack(path: $appNavigation.studentsTabNavigationPath) {
                StudentListsView()
                    .navigationDestination(for: Student.self) { student in
                        StudentDetailsView(student: student)
                    }
                    .navigationDestination(for: CustomNavigation.self) { value in
                        if value == .addStudent {
                            AddStudentView()
                        }
                    }
                    .navigationDestination(for: Int64.self) { value in
                        AddWorkActivityView(individualId: value)
                    }
            }
            .tabItem {
                Label("Students", systemImage: "graduationcap.fill")
            }
            .tag(0)
            
            NavigationStack(path: $appNavigation.activitiesTabNavigationPath) {
                WorkActivityListView()
                    .navigationDestination(for: WorkActivity.self) { activity in
                        Text(activity.name)
                    }
                    .navigationDestination(for: CustomNavigation.self) { value in
                        if value == .addActivity {
                            AddWorkActivityView()
                        }
                    }
            }
            .tabItem {
                Label("Activities", systemImage: "pencil.and.list.clipboard")
            }
            .tag(1)
        }
    }
}
