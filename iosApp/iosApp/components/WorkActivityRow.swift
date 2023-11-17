import SwiftUI
import shared

struct WorkActivityRow: View {
    
    let activity: WorkActivity
    
    var body: some View {
        HStack {
            Text(activity.name)
            Spacer()
            Image(systemName: "trash")
                .bold()
                .foregroundColor(.red)
                .onTapGesture {
                    
                }
        }
        .padding()
    }
}

#Preview {
    WorkActivityRow(
        activity: WorkActivity(
            id: Int64(36),
            name: "Daily Homework",
            description: "English Homework",
            groupKey: "ALL",
            groupValue: "ALL",
            createdDate: Kotlinx_datetimeLocalDate(year: 2023, month: Kotlinx_datetimeMonth.november, dayOfMonth: 23),
            expirationDate: Kotlinx_datetimeLocalDate(year: 2023, month: Kotlinx_datetimeMonth.november, dayOfMonth: 26)
        )
    )
}
