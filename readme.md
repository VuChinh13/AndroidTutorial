Cấu trúc ProductDetails

Đối tượng ProductDetails chứa thông tin chi tiết về sản phẩm trong ứng dụng (in-app) hoặc gói đăng ký (subscription) được lấy từ Google Play Billing API.

ProductDetails
│
├── productId: String
├── productType: String ("inapp" | "subs")
├── title: String
├── name: String
├── description: String
│
├── oneTimePurchaseOfferDetails: OneTimePurchaseOfferDetails?        // nếu là INAPP
│    ├── priceCurrencyCode: String
│    ├── formattedPrice: String
│    ├── priceAmountMicros: Long
│
└── subscriptionOfferDetails: List<SubscriptionOfferDetails>?        // nếu là SUBS
│
├── offerId: String?
├── offerToken: String               // (trước đây là offerIdToken)
├── basePlanId: String
├── offerTags: List<String>
│
└── pricingPhases: PricingPhases
│
└── pricingPhaseList: List<PricingPhase>
│
├── priceCurrencyCode: String
├── formattedPrice: String
├── priceAmountMicros: Long
├── billingCycleCount: Int
├── billingPeriod: String        // P1W, P1M, P1Y...
├── recurrenceMode: Int          // 1 = INFINITE, 2 = FINITE
├── formattedBillingPeriod: String  // (mới) hiển thị dễ đọc
└── formattedPriceAmount: String    // (mới, có thuế)

Ghi chú

oneTimePurchaseOfferDetails: chỉ có nếu sản phẩm là In-App (productType = "inapp").

subscriptionOfferDetails: chỉ có nếu sản phẩm là Subscription (productType = "subs").

formattedBillingPeriod và formattedPriceAmount là thuộc tính tùy chỉnh thêm vào để hiển thị thân thiện hơn trong giao diện người dùng.