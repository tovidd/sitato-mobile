<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class supplier extends Model
{
    protected $table= 'supplier';
    protected $primaryKey= 'id_supplier';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'nama_supplier',
        'no_telepon_supplier',
        'alamat_supplier',
        'nama_sales'
    ];
}
