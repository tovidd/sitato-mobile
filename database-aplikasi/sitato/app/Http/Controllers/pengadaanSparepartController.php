<?php

namespace sitato\Http\Controllers;

use sitato\pengadaanSparepart;
use Illuminate\Http\Request;

class pengadaanSparepartController extends Controller
{
    public function index() 
    {
        $data= pengadaanSparepart::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new pengadaanSparepart;
        $data->id_cabang= $request->id_cabang;
        $data->id_supplier= $request->id_supplier;
        $data->tanggal_pengadaan_sparepart= $request->tanggal_pengadaan_sparepart;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= pengadaanSparepart::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(pengadaanSparepart $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= pengadaanSparepart::find($id);
        if(!is_null($request->id_cabang))
        {
            $data->id_cabang= $request->id_cabang;
        }
        if(!is_null($request->id_supplier))
        {
            $data->id_supplier= $request->id_supplier;
        }
        if(!is_null($request->tanggal_pengadaan_sparepart))
        {
            $data->tanggal_pengadaan_sparepart= $request->tanggal_pengadaan_sparepart;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= pengadaanSparepart::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
