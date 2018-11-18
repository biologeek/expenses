export class Category {
    id: number;
    name: string;
    picture: string;
    pictureId: number;
    level: number;
    nomenclature: string;
    parent: Category;
}

export class Categories extends Array<Category> {}
