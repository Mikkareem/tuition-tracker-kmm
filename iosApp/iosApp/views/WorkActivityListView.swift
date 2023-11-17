import SwiftUI
import shared

struct WorkActivityListView: View {
    
    @EnvironmentObject private var appNavigation: AppNavigationObject
    
    @EnvironmentObject private var viewModel: WorkActivityViewModel
    
    var body: some View {
        VStack {
            Text("Total Activities: \(viewModel.activities.count)")
                .font(.headline)
                .foregroundColor(.blue)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal)
            
            ScrollView {
                ForEach(viewModel.activities, id: \.id) { activity in
//                    NavigationLink(value: activity) {
//                        WorkActivityRow(activity: activity)
//                    }
                    WorkActivityRow(activity: activity)
                        .onTapGesture {
                            appNavigation.addActivitiesPath(activity)
                        }
                }
            }
            .navigationTitle("Work Activities")
            .navigationBarItems(trailing: AddWorkActivityNavLink())
            .task {
                await viewModel.loadWorkActivities()
            }
        }
    }
}

//#Preview {
//    WorkActivityListView()
//}
