import Foundation
import shared
import SwiftUI

extension Date {
    func toKotlinXDatetimeLocalDate() -> Kotlinx_datetimeLocalDate {
        let components = Calendar.current.dateComponents([.day, .month, .year], from: self)
        let (day, month, year) = (components.day!, components.month!, components.year!)
        return Kotlinx_datetimeLocalDate(year: Int32(year), monthNumber: Int32(month), dayOfMonth: Int32(day))
    }
}

extension Kotlinx_datetimeLocalDate {
    func toDate() -> Date {
        let dateComponents = DateComponents(year: Int(self.year), month: Int(self.monthNumber), day: Int(self.dayOfMonth))
        let date = Calendar.current.date(from: dateComponents)
        return date!
    }
}

extension View {
    func dismissKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

extension String {
    func getFirstCharacterAsString() -> String {
        return String(self[self.startIndex])
    }
}

extension Binding<String> {
    func getActionButtons(_ values: [String]) -> [ActionSheet.Button] {
        var result: [ActionSheet.Button] = []
        for value in values {
            result.append(
                .default(Text(value), action: { [self] in
                    self.wrappedValue = value
                })
            )
        }
        
        return result
    }
}
