export class TableColumn {
    colId: number;
    colName: string;
    tableId: string;
    constructor(data: any) {
        Object.assign(this, data);
    }
}