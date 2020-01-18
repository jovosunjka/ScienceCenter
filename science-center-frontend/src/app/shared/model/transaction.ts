export interface Transaction {
    merchantOrderId: number;
    amount: number;
    currency: string;
    merchantTimestamp: string;
    timestamp: string;
    paymentType: string;
    status: string;
}
